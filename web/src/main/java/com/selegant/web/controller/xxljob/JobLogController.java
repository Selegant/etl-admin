package com.selegant.web.controller.xxljob;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.selegant.xxljob.core.constant.ReadMark;
import com.selegant.xxljob.core.exception.XxlJobException;
import com.selegant.xxljob.core.model.XxlJobGroup;
import com.selegant.xxljob.core.model.XxlJobInfo;
import com.selegant.xxljob.core.model.XxlJobLog;
import com.selegant.xxljob.core.scheduler.XxlJobScheduler;
import com.selegant.xxljob.core.util.I18nUtil;
import com.selegant.xxljob.dao.XxlJobGroupDao;
import com.selegant.xxljob.dao.XxlJobInfoDao;
import com.selegant.xxljob.dao.XxlJobLogDao;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.model.LogResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/joblog")
public class JobLogController {
	private static Logger logger = LoggerFactory.getLogger(JobLogController.class);

	@Resource
	private XxlJobGroupDao xxlJobGroupDao;
	@Resource
	public XxlJobInfoDao xxlJobInfoDao;
	@Resource
	public XxlJobLogDao xxlJobLogDao;

	@RequestMapping
	public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "0") Integer jobId) {

		// 执行器列表
		List<XxlJobGroup> jobGroupList_all =  xxlJobGroupDao.findAll();

		// filter group
		List<XxlJobGroup> jobGroupList = JobInfoController.filterJobGroupByRole(request, jobGroupList_all);
		if (jobGroupList==null || jobGroupList.size()==0) {
			throw new XxlJobException(I18nUtil.getString("jobgroup_empty"));
		}

		model.addAttribute("JobGroupList", jobGroupList);

		// 任务
		if (jobId > 0) {
			XxlJobInfo jobInfo = xxlJobInfoDao.loadById(jobId);
			if (jobInfo == null) {
				throw new RuntimeException(I18nUtil.getString("jobinfo_field_id") + I18nUtil.getString("system_unvalid"));
			}

			model.addAttribute("jobInfo", jobInfo);

			// valid permission
			JobInfoController.validPermission(request, jobInfo.getJobGroup());
		}

		return "joblog/joblog.index";
	}

	@RequestMapping("/getJobsByGroup")
	@ResponseBody
	public ReturnT<List<XxlJobInfo>> getJobsByGroup(int jobGroup){
		List<XxlJobInfo> list = xxlJobInfoDao.getJobsByGroup(jobGroup);
		return new ReturnT<List<XxlJobInfo>>(list);
	}

	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(HttpServletRequest request,
                                        @RequestParam(required = false, defaultValue = "0") int pageNo,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "0") int jobGroup,
										@RequestParam(defaultValue = "0") int jobId,
										@RequestParam(defaultValue = "-1")int logStatus,
										@RequestParam(required = false, value = "filterTime[]")String filterTime[],
										@RequestParam(required = false,defaultValue = "-1") int readMark) {

		// valid permission
//		JobInfoController.validPermission(request, jobGroup);	// 仅管理员支持查询全部；普通用户仅支持查询有权限的 jobGroup

		// parse param
		Date triggerTimeStart = null;
		Date triggerTimeEnd = null;
//		if (filterTime!=null && filterTime.trim().length()>0) {
//			String[] temp = filterTime.split(" - ");
//			if (temp.length == 2) {
//				triggerTimeStart = DateUtil.parseDateTime(temp[0]);
//				triggerTimeEnd = DateUtil.parseDateTime(temp[1]);
//			}
//		}
		if(ObjectUtil.isNotEmpty(filterTime)){
			triggerTimeStart = DateUtil.parse(filterTime[0], DatePattern.NORM_DATETIME_PATTERN);
			triggerTimeEnd = DateUtil.parse(filterTime[1], DatePattern.NORM_DATETIME_PATTERN);
		}

		int start = (pageNo-1) * pageSize;
		int length = start + pageSize;
		// page query
		List<XxlJobLog> list = xxlJobLogDao.pageList(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus, readMark);
		int list_count = xxlJobLogDao.pageListCount(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus, readMark);

		List<XxlJobInfo> xxlJobInfos = xxlJobInfoDao.getJobs();
		list.forEach(s->{
			s.setJobDesc(xxlJobInfos.stream().filter(x->x.getId()==s.getJobId()).findFirst().get().getJobDesc());
		});

		// package result
		Map<String, Object> maps = new HashMap<>(16);

		maps.put("pageNo",pageNo);
		maps.put("pageSize",pageSize);
		maps.put("totalCount", list_count);		// 总记录数
		maps.put("totalPage", list_count);	// 过滤后的总记录数
		maps.put("data", list);  					// 分页列表
		return maps;
	}

	@RequestMapping("/logDetailPage")
	public String logDetailPage(int id, Model model){

		// base check
		ReturnT<String> logStatue = ReturnT.SUCCESS;
		XxlJobLog jobLog = xxlJobLogDao.load(id);
		if (jobLog == null) {
            throw new RuntimeException(I18nUtil.getString("joblog_logid_unvalid"));
		}

        model.addAttribute("triggerCode", jobLog.getTriggerCode());
        model.addAttribute("handleCode", jobLog.getHandleCode());
        model.addAttribute("executorAddress", jobLog.getExecutorAddress());
        model.addAttribute("triggerTime", jobLog.getTriggerTime().getTime());
        model.addAttribute("logId", jobLog.getId());
		return "joblog/joblog.detail";
	}

	@RequestMapping("/logDetailCat")
	@ResponseBody
	public ReturnT<LogResult> logDetailCat(String executorAddress, long triggerTime, long logId, int fromLineNum){
		try {
			ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(executorAddress);
			ReturnT<LogResult> logResult = executorBiz.log(triggerTime, logId, fromLineNum);

			// is end
            if (logResult.getContent()!=null && logResult.getContent().getFromLineNum() > logResult.getContent().getToLineNum()) {
                XxlJobLog jobLog = xxlJobLogDao.load(logId);
                if (jobLog.getHandleCode() > 0) {
                    logResult.getContent().setEnd(true);
                }
            }
			logResult.getContent().setLogContent(logResult.getContent().getLogContent().replace("\n","<br/>"));
			return logResult;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnT<LogResult>(ReturnT.FAIL_CODE, e.getMessage());
		}
	}

	@RequestMapping("/logKill")
	@ResponseBody
	public ReturnT<String> logKill(int id){
		// base check
		XxlJobLog log = xxlJobLogDao.load(id);
		XxlJobInfo jobInfo = xxlJobInfoDao.loadById(log.getJobId());
		if (jobInfo==null) {
			return new ReturnT<String>(500, I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
		}
		if (ReturnT.SUCCESS_CODE != log.getTriggerCode()) {
			return new ReturnT<String>(500, I18nUtil.getString("joblog_kill_log_limit"));
		}

		// request of kill
		ReturnT<String> runResult = null;
		try {
			ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(log.getExecutorAddress());
			runResult = executorBiz.kill(jobInfo.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			runResult = new ReturnT<String>(500, e.getMessage());
		}

		if (ReturnT.SUCCESS_CODE == runResult.getCode()) {
			log.setHandleCode(ReturnT.FAIL_CODE);
			log.setReadMark(ReadMark.UN_READ);
			log.setHandleMsg( I18nUtil.getString("joblog_kill_log_byman")+":" + (runResult.getMsg()!=null?runResult.getMsg():""));
			log.setHandleTime(new Date());
			xxlJobLogDao.updateHandleInfo(log);
			return new ReturnT<String>(runResult.getMsg());
		} else {
			return new ReturnT<String>(500, runResult.getMsg());
		}
	}

	@RequestMapping("/clearLog")
	@ResponseBody
	public ReturnT<String> clearLog(int jobGroup, int jobId, int type){

		Date clearBeforeTime = null;
		int clearBeforeNum = 0;
		if (type == 1) {
			clearBeforeTime = DateUtil.addMonths(new Date(), -1);	// 清理一个月之前日志数据
		} else if (type == 2) {
			clearBeforeTime = DateUtil.addMonths(new Date(), -3);	// 清理三个月之前日志数据
		} else if (type == 3) {
			clearBeforeTime = DateUtil.addMonths(new Date(), -6);	// 清理六个月之前日志数据
		} else if (type == 4) {
			clearBeforeTime = DateUtil.addYears(new Date(), -1);	// 清理一年之前日志数据
		} else if (type == 5) {
			clearBeforeNum = 1000;		// 清理一千条以前日志数据
		} else if (type == 6) {
			clearBeforeNum = 10000;		// 清理一万条以前日志数据
		} else if (type == 7) {
			clearBeforeNum = 30000;		// 清理三万条以前日志数据
		} else if (type == 8) {
			clearBeforeNum = 100000;	// 清理十万条以前日志数据
		} else if (type == 9) {
			clearBeforeNum = 0;			// 清理所有日志数据
		} else {
			return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("joblog_clean_type_unvalid"));
		}

		List<Long> logIds = null;
		do {
			logIds = xxlJobLogDao.findClearLogIds(jobGroup, jobId, clearBeforeTime, clearBeforeNum, 1000);
			if (logIds!=null && logIds.size()>0) {
				xxlJobLogDao.clearLog(logIds);
			}
		} while (logIds!=null && logIds.size()>0);

		return ReturnT.SUCCESS;
	}


	@RequestMapping("/getUnReadCount")
	@ResponseBody
	public ReturnT<Integer> getUnReadCount(){
		return new ReturnT<Integer>(xxlJobLogDao.getUnReadCount());
	}

	@RequestMapping("/readLog")
	@ResponseBody
	public ReturnT<String> readLog(Long logId){
		XxlJobLog xxlJobLog = new XxlJobLog();
		xxlJobLog.setId(logId);
		xxlJobLog.setReadMark(ReadMark.HAVE_READ);
		xxlJobLogDao.updateReadMark(xxlJobLog);
		return ReturnT.SUCCESS;
	}

	@RequestMapping("/readBatchLog")
	@ResponseBody
	public ReturnT<String> readBatchLog(String logIds){
		if(StrUtil.isBlank(logIds)){
			return ReturnT.FAIL;
		}
		String[] strings = null;
		if(logIds.contains(",")){
			strings = logIds.split(",");
		}else {
			XxlJobLog xxlJobLog = new XxlJobLog();
			xxlJobLog.setId(Integer.parseInt(logIds));
			xxlJobLog.setReadMark(ReadMark.HAVE_READ);
			xxlJobLogDao.updateReadMark(xxlJobLog);
			return ReturnT.SUCCESS;
		}
		for (String id : strings) {
			XxlJobLog xxlJobLog = new XxlJobLog();
			xxlJobLog.setId(Integer.parseInt(id));
			xxlJobLog.setReadMark(ReadMark.HAVE_READ);
			xxlJobLogDao.updateReadMark(xxlJobLog);
		}
		return ReturnT.SUCCESS;
	}


}
