package com.selegant.web.controller.xxljob;

import com.selegant.common.base.Result;
import com.selegant.common.util.ResultUtil;
import com.selegant.xxljob.core.cron.CronExpression;
import com.selegant.xxljob.core.model.XxlJobGroup;
import com.selegant.xxljob.core.model.XxlJobInfo;
import com.selegant.xxljob.core.model.XxlJobUser;
import com.selegant.xxljob.core.route.ExecutorRouteStrategyEnum;
import com.selegant.xxljob.core.thread.JobTriggerPoolHelper;
import com.selegant.xxljob.core.trigger.TriggerTypeEnum;
import com.selegant.xxljob.core.util.I18nUtil;
import com.selegant.xxljob.dao.XxlJobGroupDao;
import com.selegant.xxljob.service.LoginService;
import com.selegant.xxljob.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.xxl.job.core.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {

	@Resource
	private XxlJobGroupDao xxlJobGroupDao;
	@Resource
	private XxlJobService xxlJobService;

	@GetMapping("selectInfo")
	@ResponseBody
	public Result index(HttpServletRequest request, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

		Map<String,Object> result = new HashMap<>();

		// 枚举-字典
		result.put("executorRouteStrategyEnum", ExecutorRouteStrategyEnum.values());	    // 路由策略-列表
		result.put("glueTypeEnum", GlueTypeEnum.values());								// Glue类型-字典
		result.put("executorBlockStrategyEnum", ExecutorBlockStrategyEnum.values());	    // 阻塞处理策略-字典

		// 执行器列表
		List<XxlJobGroup> jobGroupList_all =  xxlJobGroupDao.findAll();

		// filter group
//		List<XxlJobGroup> jobGroupList = filterJobGroupByRole(request, jobGroupList_all);
//		if (jobGroupList==null || jobGroupList.size()==0) {
//			throw new XxlJobException(I18nUtil.getString("jobgroup_empty"));
//		}
		// 暂时不过滤
		List<XxlJobGroup> jobGroupList = jobGroupList_all;

		result.put("jobGroupList", jobGroupList);
		result.put("jobGroup", jobGroupList.stream().findFirst().get().getId());

		return ResultUtil.setSuccess(result);

	}

	public static List<XxlJobGroup> filterJobGroupByRole(HttpServletRequest request, List<XxlJobGroup> jobGroupList_all){
		List<XxlJobGroup> jobGroupList = new ArrayList<>();
		if (jobGroupList_all!=null && jobGroupList_all.size()>0) {
			XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
			if (loginUser.getRole() == 1) {
				jobGroupList = jobGroupList_all;
			} else {
				List<String> groupIdStrs = new ArrayList<>();
				if (loginUser.getPermission()!=null && loginUser.getPermission().trim().length()>0) {
					groupIdStrs = Arrays.asList(loginUser.getPermission().trim().split(","));
				}
				for (XxlJobGroup groupItem:jobGroupList_all) {
					if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
						jobGroupList.add(groupItem);
					}
				}
			}
		}
		return jobGroupList;
	}
	public static void validPermission(HttpServletRequest request, int jobGroup) {
		XxlJobUser loginUser = (XxlJobUser) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
		if (!loginUser.validPermission(jobGroup)) {
			throw new RuntimeException(I18nUtil.getString("system_permission_limit") + "[username="+ loginUser.getUsername() +"]");
		}
	}

	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int pageNo,
										@RequestParam(required = false, defaultValue = "10") int pageSize,
										@RequestParam(defaultValue = "-1") int jobGroup, @RequestParam(defaultValue = "-1") int triggerStatus,
										String objectType,
										String jobDesc,
										String executorHandler,
										String author,
										String cron,
										String sortField,
										String sortOrder) {
		if ("-1".equals(cron)) {
			cron = null;
		}
		return xxlJobService.pageList(pageNo, pageSize, jobGroup, triggerStatus, jobDesc, executorHandler, author, objectType, cron,sortField,sortOrder);
	}


	@RequestMapping("/list")
	@ResponseBody
	public ReturnT<List<XxlJobInfo>> list() {
		return xxlJobService.list();
	}

	@RequestMapping("/add")
	@ResponseBody
	public ReturnT<String> add(@RequestBody XxlJobInfo jobInfo) {
		return xxlJobService.add(jobInfo);
	}

	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(@RequestBody XxlJobInfo jobInfo) {
		return xxlJobService.update(jobInfo);
	}

	@RequestMapping("/batchUpdateCron")
	@ResponseBody
	public ReturnT<String> batchUpdateCron(@RequestBody Map<String,String> params) {
		String ids = params.get("ids");
		String cron = params.get("cron");
		return xxlJobService.batchUpdateCron(ids,cron);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public ReturnT<String> remove(int id) {
		return xxlJobService.remove(id);
	}

	@RequestMapping("/stop")
	@ResponseBody
	public ReturnT<String> pause(int id) {
		return xxlJobService.stop(id);
	}

	@RequestMapping("/start")
	@ResponseBody
	public ReturnT<String> start(int id) {
		return xxlJobService.start(id);
	}

	@RequestMapping("/trigger")
	@ResponseBody
	//@PermissionLimit(limit = false)
	public ReturnT<String> triggerJob(int id, String executorParam) {
		// force cover job param
		if (executorParam == null) {
			executorParam = "";
		}

		JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam);
		return ReturnT.SUCCESS;
	}

	@RequestMapping("/nextTriggerTime")
	@ResponseBody
	public ReturnT<List<String>> nextTriggerTime(String cron) {
		List<String> result = new ArrayList<>();
		try {
			CronExpression cronExpression = new CronExpression(cron);
			Date lastTime = new Date();
			for (int i = 0; i < 5; i++) {
				lastTime = cronExpression.getNextValidTimeAfter(lastTime);
				if (lastTime != null) {
					result.add(DateUtil.formatDateTime(lastTime));
				} else {
					break;
				}
			}
		} catch (ParseException e) {
			return new ReturnT<List<String>>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid"));
		}
		return new ReturnT<List<String>>(result);
	}

	@RequestMapping("/restartAll")
	@ResponseBody
	public ReturnT<String> restartAll() {
		return xxlJobService.restartAll();
	}

	@RequestMapping("/stopAll")
	@ResponseBody
	public ReturnT<String> stopAll() {
		return xxlJobService.stopAll();
	}

	@RequestMapping("/startBatch")
	@ResponseBody
	public ReturnT<String> startBatch(String jobIds) {
		return xxlJobService.startBatch(jobIds);
	}


	@RequestMapping("/stopBatch")
	@ResponseBody
	public ReturnT<String> stopBatch(String jobIds) {
		return xxlJobService.stopBatch(jobIds);
	}
}
