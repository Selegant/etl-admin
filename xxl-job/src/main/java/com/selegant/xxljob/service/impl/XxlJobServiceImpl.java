package com.selegant.xxljob.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.selegant.xxljob.core.cron.CronExpression;
import com.selegant.xxljob.core.model.XxlJobGroup;
import com.selegant.xxljob.core.model.XxlJobInfo;
import com.selegant.xxljob.core.model.XxlJobLog;
import com.selegant.xxljob.core.model.XxlJobLogReport;
import com.selegant.xxljob.core.route.ExecutorRouteStrategyEnum;
import com.selegant.xxljob.core.thread.JobScheduleHelper;
import com.selegant.xxljob.core.util.I18nUtil;
import com.selegant.xxljob.dao.*;
import com.selegant.xxljob.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * core job action for xxl-job
 * @author xuxueli 2016-5-28 15:30:33
 */
@Service
public class XxlJobServiceImpl implements XxlJobService {
	private static Logger logger = LoggerFactory.getLogger(XxlJobServiceImpl.class);

	@Resource
	private XxlJobGroupDao xxlJobGroupDao;
	@Resource
	private XxlJobInfoDao xxlJobInfoDao;
	@Resource
	public XxlJobLogDao xxlJobLogDao;
	@Resource
	private XxlJobLogGlueDao xxlJobLogGlueDao;
	@Resource
	private XxlJobLogReportDao xxlJobLogReportDao;

	@Override
	public Map<String, Object> pageList(int pageNo, int pageSize, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author,String objectType,String cron) {

		int start = (pageNo-1) * pageSize;
		// page list
		List<XxlJobInfo> list = xxlJobInfoDao.pageList(start, pageSize, jobGroup, triggerStatus, jobDesc, executorHandler, author, objectType, cron);
		int list_count = xxlJobInfoDao.pageListCount(start, pageSize, jobGroup, triggerStatus, jobDesc, executorHandler, author, objectType, cron);
		// package result
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("pageNo",pageNo);
		maps.put("pageSize",pageSize);
	    maps.put("totalCount", list_count);		// 总记录数
	    maps.put("totalPage", list_count);	// 过滤后的总记录数
	    maps.put("data", list);  					// 分页列表
		return maps;
	}

	@Override
	public ReturnT<String> add(XxlJobInfo jobInfo) {
		// valid
		XxlJobGroup group = xxlJobGroupDao.load(jobInfo.getJobGroup());
		if (group == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_choose")+I18nUtil.getString("jobinfo_field_jobgroup")) );
		}
		if (!CronExpression.isValidExpression(jobInfo.getJobCron())) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid") );
		}
		if (jobInfo.getJobDesc()==null || jobInfo.getJobDesc().trim().length()==0) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_jobdesc")) );
		}
		if (jobInfo.getAuthor()==null || jobInfo.getAuthor().trim().length()==0) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_author")) );
		}
		if (ExecutorRouteStrategyEnum.match(jobInfo.getExecutorRouteStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorRouteStrategy")+I18nUtil.getString("system_unvalid")) );
		}
		if (ExecutorBlockStrategyEnum.match(jobInfo.getExecutorBlockStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorBlockStrategy")+I18nUtil.getString("system_unvalid")) );
		}
		if (GlueTypeEnum.match(jobInfo.getGlueType()) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_gluetype")+I18nUtil.getString("system_unvalid")) );
		}
		if (GlueTypeEnum.BEAN==GlueTypeEnum.match(jobInfo.getGlueType()) && (jobInfo.getExecutorHandler()==null || jobInfo.getExecutorHandler().trim().length()==0) ) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+"JobHandler") );
		}

		// fix "\r" in shell
		if (GlueTypeEnum.GLUE_SHELL==GlueTypeEnum.match(jobInfo.getGlueType()) && jobInfo.getGlueSource()!=null) {
			jobInfo.setGlueSource(jobInfo.getGlueSource().replaceAll("\r", ""));
		}

		// ChildJobId valid
        if (jobInfo.getChildJobId()!=null && jobInfo.getChildJobId().trim().length()>0) {
			String[] childJobIds = jobInfo.getChildJobId().split(",");
			for (String childJobIdItem: childJobIds) {
				if (childJobIdItem!=null && childJobIdItem.trim().length()>0 && isNumeric(childJobIdItem)) {
					XxlJobInfo childJobInfo = xxlJobInfoDao.loadById(Integer.parseInt(childJobIdItem));
					if (childJobInfo==null) {
						return new ReturnT<String>(ReturnT.FAIL_CODE,
								MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_not_found")), childJobIdItem));
					}
				} else {
					return new ReturnT<String>(ReturnT.FAIL_CODE,
							MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_unvalid")), childJobIdItem));
				}
			}

			// join , avoid "xxx,,"
			String temp = "";
			for (String item:childJobIds) {
				temp += item + ",";
			}
			temp = temp.substring(0, temp.length()-1);

			jobInfo.setChildJobId(temp);
		}

		// add in db
		jobInfo.setAddTime(new Date());
		jobInfo.setUpdateTime(new Date());
		jobInfo.setGlueUpdatetime(new Date());
		xxlJobInfoDao.save(jobInfo);
		if (jobInfo.getId() < 1) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_add")+I18nUtil.getString("system_fail")) );
		}

		return new ReturnT<String>(String.valueOf(jobInfo.getId()));
	}

	private boolean isNumeric(String str){
		try {
			int result = Integer.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public ReturnT<String> update(XxlJobInfo jobInfo) {

		// valid
		if (!CronExpression.isValidExpression(jobInfo.getJobCron())) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid") );
		}
		if (jobInfo.getJobDesc()==null || jobInfo.getJobDesc().trim().length()==0) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_jobdesc")) );
		}
		if (jobInfo.getAuthor()==null || jobInfo.getAuthor().trim().length()==0) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_author")) );
		}
		if (ExecutorRouteStrategyEnum.match(jobInfo.getExecutorRouteStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorRouteStrategy")+I18nUtil.getString("system_unvalid")) );
		}
		if (ExecutorBlockStrategyEnum.match(jobInfo.getExecutorBlockStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorBlockStrategy")+I18nUtil.getString("system_unvalid")) );
		}

		// ChildJobId valid
        if (jobInfo.getChildJobId()!=null && jobInfo.getChildJobId().trim().length()>0) {
			String[] childJobIds = jobInfo.getChildJobId().split(",");
			for (String childJobIdItem: childJobIds) {
				if (childJobIdItem!=null && childJobIdItem.trim().length()>0 && isNumeric(childJobIdItem)) {
					XxlJobInfo childJobInfo = xxlJobInfoDao.loadById(Integer.parseInt(childJobIdItem));
					if (childJobInfo==null) {
						return new ReturnT<String>(ReturnT.FAIL_CODE,
								MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_not_found")), childJobIdItem));
					}
				} else {
					return new ReturnT<String>(ReturnT.FAIL_CODE,
							MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_unvalid")), childJobIdItem));
				}
			}

			// join , avoid "xxx,,"
			String temp = "";
			for (String item:childJobIds) {
				temp += item + ",";
			}
			temp = temp.substring(0, temp.length()-1);

			jobInfo.setChildJobId(temp);
		}

		// group valid
		XxlJobGroup jobGroup = xxlJobGroupDao.load(jobInfo.getJobGroup());
		if (jobGroup == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_jobgroup")+I18nUtil.getString("system_unvalid")) );
		}

		// stage job info
		XxlJobInfo exists_jobInfo = xxlJobInfoDao.loadById(jobInfo.getId());
		if (exists_jobInfo == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_id")+I18nUtil.getString("system_not_found")) );
		}

		// next trigger time (5s后生效，避开预读周期)
		long nextTriggerTime = exists_jobInfo.getTriggerNextTime();
		if (exists_jobInfo.getTriggerStatus() == 1 && !jobInfo.getJobCron().equals(exists_jobInfo.getJobCron()) ) {
			try {
				Date nextValidTime = new CronExpression(jobInfo.getJobCron()).getNextValidTimeAfter(new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
				if (nextValidTime == null) {
					return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_never_fire"));
				}
				nextTriggerTime = nextValidTime.getTime();
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
				return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid")+" | "+ e.getMessage());
			}
		}

		exists_jobInfo.setJobGroup(jobInfo.getJobGroup());
		exists_jobInfo.setJobCron(jobInfo.getJobCron());
		exists_jobInfo.setJobDesc(jobInfo.getJobDesc());
		exists_jobInfo.setAuthor(jobInfo.getAuthor());
		exists_jobInfo.setAlarmEmail(jobInfo.getAlarmEmail());
		exists_jobInfo.setExecutorRouteStrategy(jobInfo.getExecutorRouteStrategy());
		exists_jobInfo.setExecutorHandler(jobInfo.getExecutorHandler());
		exists_jobInfo.setExecutorParam(jobInfo.getExecutorParam());
		exists_jobInfo.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
		exists_jobInfo.setExecutorTimeout(jobInfo.getExecutorTimeout());
		exists_jobInfo.setExecutorFailRetryCount(jobInfo.getExecutorFailRetryCount());
		exists_jobInfo.setChildJobId(jobInfo.getChildJobId());
		exists_jobInfo.setTriggerNextTime(nextTriggerTime);

		exists_jobInfo.setUpdateTime(new Date());
        xxlJobInfoDao.update(exists_jobInfo);


		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> remove(int id) {
		XxlJobInfo xxlJobInfo = xxlJobInfoDao.loadById(id);
		if (xxlJobInfo == null) {
			return ReturnT.SUCCESS;
		}

		xxlJobInfoDao.delete(id);
		xxlJobLogDao.delete(id);
		xxlJobLogGlueDao.deleteByJobId(id);
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> start(int id) {
		XxlJobInfo xxlJobInfo = xxlJobInfoDao.loadById(id);

		// next trigger time (5s后生效，避开预读周期)
		long nextTriggerTime = 0;
		try {
			Date nextValidTime = new CronExpression(xxlJobInfo.getJobCron()).getNextValidTimeAfter(new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
			if (nextValidTime == null) {
				return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_never_fire"));
			}
			nextTriggerTime = nextValidTime.getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid")+" | "+ e.getMessage());
		}

		xxlJobInfo.setTriggerStatus(1);
		xxlJobInfo.setTriggerLastTime(0);
		xxlJobInfo.setTriggerNextTime(nextTriggerTime);

		xxlJobInfo.setUpdateTime(new Date());
		xxlJobInfoDao.update(xxlJobInfo);
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> stop(int id) {
        XxlJobInfo xxlJobInfo = xxlJobInfoDao.loadById(id);

		xxlJobInfo.setTriggerStatus(0);
		xxlJobInfo.setTriggerLastTime(0);
		xxlJobInfo.setTriggerNextTime(0);

		xxlJobInfo.setUpdateTime(new Date());
		xxlJobInfoDao.update(xxlJobInfo);
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<Map<String,Object>> dashboardInfo() {

		int jobInfoCount = xxlJobInfoDao.findAllCount();
		int jobCount = xxlJobInfoDao.findTypeCount(2);
		int transCount = jobInfoCount - jobCount;
		int jobLogCount = 0;
		int jobLogSuccessCount = 0;
		int jobLogFailCount = 0;
		int jobRunningCount = 0;
		XxlJobLogReport xxlJobLogReport = xxlJobLogReportDao.queryLogReportTotal();
		if (xxlJobLogReport != null) {
			jobLogCount = xxlJobLogReport.getRunningCount() + xxlJobLogReport.getSucCount() + xxlJobLogReport.getFailCount();
			jobLogSuccessCount = xxlJobLogReport.getSucCount();
			jobLogFailCount = xxlJobLogReport.getFailCount();
			jobRunningCount = xxlJobLogReport.getRunningCount();
		}

		// executor count
		Set<String> executorAddressSet = new HashSet<String>();
		List<XxlJobGroup> groupList = xxlJobGroupDao.findAll();

		if (groupList!=null && !groupList.isEmpty()) {
			for (XxlJobGroup group: groupList) {
				if (group.getRegistryList()!=null && !group.getRegistryList().isEmpty()) {
					executorAddressSet.addAll(group.getRegistryList());
				}
			}
		}

		int executorCount = executorAddressSet.size();

		Map<String, Object> dashboardMap = new HashMap<String, Object>();
		dashboardMap.put("jobInfoCount", jobInfoCount);
		dashboardMap.put("jobLogCount", jobLogCount);
		dashboardMap.put("jobLogSuccessCount", jobLogSuccessCount);
		dashboardMap.put("jobLogFailCount",jobLogFailCount);
		dashboardMap.put("jobRunningCount",jobRunningCount);
		dashboardMap.put("executorCount", executorCount);
		dashboardMap.put("jobCount",jobCount);
		dashboardMap.put("transCount",transCount);
		return new ReturnT<>(dashboardMap);
	}

	@Override
	public ReturnT<List<Map<String, Object>>> chartInfo(String startDate, String endDate) {
		List<Map<String, Object>> triggerList = new ArrayList<>(16);
		Date start = cn.hutool.core.date.DateUtil.parse(startDate, DatePattern.NORM_DATE_PATTERN);
		Date end = cn.hutool.core.date.DateUtil.parse(endDate, DatePattern.NORM_DATE_PATTERN);
		end = DateUtil.offsetDay(end,1);
		List<XxlJobLog> list = xxlJobLogDao.getMonitorLogInfo(start,end);

		long days = DateUtil.betweenDay(start,end,true);

		for (int i = 0; i < days ; i++) {
			Date nowDate = DateUtil.offsetDay(start,i);
			logger.info(DateUtil.format(nowDate,DatePattern.NORM_DATE_PATTERN));
			List<XxlJobLog> dayList = list.stream().filter(s->DateUtil.format(nowDate,DatePattern.NORM_DATE_PATTERN).equals(DateUtil.format(s.getTriggerTime(),DatePattern.NORM_DATE_PATTERN))).collect(Collectors.toList());
			int triggerDayCountRunning = 0;
			int triggerDayCountSuc = 0;
			int triggerDayCountFail = 0;
			Map<String,Object> triggerMap = new HashMap<>(16);
			if (!dayList.isEmpty()) {
				triggerDayCountRunning = Math.toIntExact(dayList.stream().filter(s -> 0 == s.getHandleCode() && 500 != s.getTriggerCode()).count());
				triggerDayCountSuc = Math.toIntExact(dayList.stream().filter(s -> 200 == s.getHandleCode()).count());
				triggerDayCountFail = Math.toIntExact(dayList.stream().filter(s -> 500 == s.getHandleCode()  || 500 == s.getTriggerCode()).count());
			}
			triggerMap.put("日期",DateUtil.format(nowDate,DatePattern.NORM_DATE_PATTERN));
			triggerMap.put("成功次数",triggerDayCountSuc);
			triggerMap.put("失败次数",triggerDayCountFail);
			triggerMap.put("正在运行数",triggerDayCountRunning);
			triggerList.add(triggerMap);
		}

		return new ReturnT<>(triggerList);
	}

	@Override
	public ReturnT<Map<String, Object>> monitorInfo() {
		XxlJobLogReport xxlJobLogReport = xxlJobLogReportDao.queryLogReportTodayTotal();
		Map<String, Object> result = new HashMap<String, Object>();
		if (xxlJobLogReport != null) {
			result.put("todaySumCount", xxlJobLogReport.getSucCount()+xxlJobLogReport.getFailCount()+xxlJobLogReport.getRunningCount());
			result.put("todayFailCount", xxlJobLogReport.getFailCount());
			result.put("todaySuccessCount", xxlJobLogReport.getSucCount());
		}
		return new ReturnT<Map<String, Object>>(result);
	}

	@Override
	public ReturnT<String> restartAll() {
		List<XxlJobInfo> list = xxlJobInfoDao.getJobs();
		list.forEach(s->{
			stop(s.getId());
			start(s.getId());
		});
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> stopAll() {
		List<XxlJobInfo> list = xxlJobInfoDao.getJobs();
		list.forEach(s->{
			stop(s.getId());
		});
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<List<Map<String, Object>>> monitorJobTypeInfo() {
		List<Map<String, Object>> result = new ArrayList<>(16);
		List<XxlJobInfo> list = xxlJobInfoDao.getJobs();
		Map<Integer,String> jobType = new HashMap<>(16);
		jobType.put(1,"Kettle作业");
		jobType.put(2,"Kettle转换");
		jobType.put(3,"普通作业");
		jobType.forEach((k,v)->{
			Map<String,Object> jobTypeMap = new HashMap<>(16);
			jobTypeMap.put("类型",jobType.get(k));
			jobTypeMap.put("数量",list.stream().filter(s->k.equals(s.getObjectType())).count());
			result.add(jobTypeMap);
		});
		return new ReturnT<>(result);
	}

	@Override
	public ReturnT<List<Map<String, Object>>> monitorJobExecInfo() {
		List<Map<String, Object>> result = new ArrayList<>(16);
		int jobLogSuccessCount = 0;
		int jobLogFailCount = 0;
		int jobRunningCount = 0;
		XxlJobLogReport xxlJobLogReport = xxlJobLogReportDao.queryLogReportTotal();
		if (xxlJobLogReport != null) {
			jobLogSuccessCount = xxlJobLogReport.getSucCount();
			jobLogFailCount = xxlJobLogReport.getFailCount();
			jobRunningCount = xxlJobLogReport.getRunningCount();
		}
		Map<Integer,String> type = new HashMap<>(16);
		type.put(1,"成功次数");
		type.put(2,"失败次数");
		type.put(3,"运行中");
		int finalJobLogSuccessCount = jobLogSuccessCount;
		int finalJobLogFailCount = jobLogFailCount;
		int finalJobRunningCount = jobRunningCount;
		type.forEach((k, v)->{
			Map<String,Object> jobTypeMap = new HashMap<>(16);
			jobTypeMap.put("类型",type.get(k));
			if(1==k){
				jobTypeMap.put("数量", finalJobLogSuccessCount);
			}
			if(2==k){
				jobTypeMap.put("数量", finalJobLogFailCount);
			}
			if(3==k){
				jobTypeMap.put("数量", finalJobRunningCount);
			}

			result.add(jobTypeMap);
		});
		return new ReturnT<>(result);
	}

	@Override
	public ReturnT<Map<String, Object>> monitorTaskExecInfo(String startDate, String endDate, String jobId) {
		Map<String, Object> result = new HashMap<>(16);
		List<Map<String, Object>> monitorTaskInfos = new ArrayList<>(16);
		Date start = cn.hutool.core.date.DateUtil.parse(startDate, DatePattern.NORM_DATE_PATTERN);
		Date end = cn.hutool.core.date.DateUtil.parse(endDate, DatePattern.NORM_DATE_PATTERN);

		List<XxlJobLog> xxlJobLogs = xxlJobLogDao.getMonitorLogInfo(start,end);

		List<XxlJobInfo> jobInfos = xxlJobInfoDao.getJobs();
		if (StrUtil.isBlank(jobId)){
			jobInfos.forEach(xxlJobInfo -> {
				Map<String, Object> resultMap = new HashMap<>(16);
				resultMap.put("作业",xxlJobInfo.getJobDesc());
				List<XxlJobLog> logs = xxlJobLogs.stream().filter(s->s.getJobId()== xxlJobInfo.getId()).collect(Collectors.toList());
				int triggerDayCountRunning = 0;
				int triggerDayCountSuc = 0;
				int triggerDayCountFail = 0;
				if (!logs.isEmpty()) {
					triggerDayCountRunning = Math.toIntExact(logs.stream().filter(s -> 0 == s.getHandleCode() && 500 != s.getTriggerCode()).count());
					triggerDayCountSuc = Math.toIntExact(logs.stream().filter(s -> 200 == s.getHandleCode()).count());
					triggerDayCountFail = Math.toIntExact(logs.stream().filter(s -> 500 == s.getHandleCode()  || 500 == s.getTriggerCode()).count());
				}
				resultMap.put("成功次数",triggerDayCountSuc);
				resultMap.put("失败次数",triggerDayCountFail);
				resultMap.put("运行中",triggerDayCountRunning);
				monitorTaskInfos.add(resultMap);
			});
		}else {
			XxlJobInfo xxlJobInfo = jobInfos.stream().filter(s->Integer.parseInt(jobId) == s.getId()).findFirst().get();
			Map<String, Object> resultMap = new HashMap<>(16);
			resultMap.put("作业",xxlJobInfo.getJobDesc());
			List<XxlJobLog> logs = xxlJobLogs.stream().filter(s->s.getJobId()==xxlJobInfo.getId()).collect(Collectors.toList());
			int triggerDayCountRunning = 0;
			int triggerDayCountSuc = 0;
			int triggerDayCountFail = 0;
			if (!logs.isEmpty()) {
				triggerDayCountRunning = Math.toIntExact(logs.stream().filter(s -> 0 == s.getHandleCode() && 500 != s.getTriggerCode()).count());
				triggerDayCountSuc = Math.toIntExact(logs.stream().filter(s -> 200 == s.getHandleCode()).count());
				triggerDayCountFail = Math.toIntExact(logs.stream().filter(s -> 500 == s.getHandleCode()  || 500 == s.getTriggerCode()).count());
			}
			resultMap.put("成功次数",triggerDayCountSuc);
			resultMap.put("失败次数",triggerDayCountFail);
			resultMap.put("运行中",triggerDayCountRunning);
			monitorTaskInfos.add(resultMap);
			result.put("monitorTaskTable",logs.stream().filter(s -> 500 == s.getHandleCode()).collect(Collectors.toList()));
		}
		result.put("chartInfo",monitorTaskInfos);
		return new ReturnT<>(result);
	}

	@Override
	public ReturnT<List<XxlJobInfo>> list() {
		return new ReturnT<>(xxlJobInfoDao.getJobs());
	}

	@Override
	public ReturnT<List<Map<String, Object>>> monitorJobStatusInfo() {
		List<Map<String, Object>> result = new ArrayList<>(16);
		List<XxlJobInfo> jobInfos = xxlJobInfoDao.getJobs();
		Map<String,Object> stopMap = new HashMap<>(16);
		stopMap.put("运行状态","停止");
		stopMap.put("数量",jobInfos.stream().filter(s->s.getTriggerStatus()==0).count());
		Map<String,Object> runMap = new HashMap<>(16);
		runMap.put("运行状态","运行");
		runMap.put("数量",jobInfos.stream().filter(s->s.getTriggerStatus()==1).count());
		result.add(stopMap);
		result.add(runMap);
		return new ReturnT<>(result);
	}

	@Override
	public ReturnT<String> batchUpdateCron(String ids, String cron) {
		if(StrUtil.isBlank(ids)){
			return ReturnT.FAIL;
		}
		String[] strings = null;
		if(ids.contains(",")){
			strings = ids.split(",");
		}else {
			XxlJobInfo xxlJobInfo = new XxlJobInfo();
			xxlJobInfo.setId(Integer.parseInt(ids));
			xxlJobInfo.setJobCron(cron);
			xxlJobInfoDao.update(xxlJobInfo);
		}
		for (String id : strings) {
			XxlJobInfo xxlJobInfo = new XxlJobInfo();
			xxlJobInfo.setId(Integer.parseInt(id));
			xxlJobInfo.setJobCron(cron);
			xxlJobInfoDao.update(xxlJobInfo);
		}
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> startBatch(String jobIds) {
		if(StrUtil.isBlank(jobIds)){
			return ReturnT.FAIL;
		}
		String[] strings = null;
		if(jobIds.contains(",")){
			strings = jobIds.split(",");
		}else {
			return start(Integer.parseInt(jobIds));
		}
		for (String id : strings) {
			start(Integer.parseInt(id));
		}
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> stopBatch(String jobIds) {
		if(StrUtil.isBlank(jobIds)){
			return ReturnT.FAIL;
		}
		String[] strings = null;
		if(jobIds.contains(",")){
			strings = jobIds.split(",");
		}else {
			return stop(Integer.parseInt(jobIds));
		}
		for (String id : strings) {
			stop(Integer.parseInt(id));
		}
		return ReturnT.SUCCESS;
	}

}
