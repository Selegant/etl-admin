package com.selegant.xxljob.service;


import com.selegant.xxljob.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * core job action for xxl-job
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface XxlJobService {

	/**
	 * page list
	 *
	 * @param start
	 * @param length
	 * @param jobGroup
	 * @param jobDesc
	 * @param executorHandler
	 * @param author
	 * @return
	 */
	public Map<String, Object> pageList(int pageNo, int pageSize, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author,String objectType);

	/**
	 * add job
	 *
	 * @param jobInfo
	 * @return
	 */
	public ReturnT<String> add(XxlJobInfo jobInfo);

	/**
	 * update job
	 *
	 * @param jobInfo
	 * @return
	 */
	public ReturnT<String> update(XxlJobInfo jobInfo);

	/**
	 * remove job
	 * 	 *
	 * @param id
	 * @return
	 */
	public ReturnT<String> remove(int id);

	/**
	 * start job
	 *
	 * @param id
	 * @return
	 */
	public ReturnT<String> start(int id);

	/**
	 * stop job
	 *
	 * @param id
	 * @return
	 */
	public ReturnT<String> stop(int id);

	/**
	 * dashboard info
	 *
	 * @return
	 */
	public ReturnT<Map<String,Object>> dashboardInfo();

	/**
	 * chart info
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ReturnT<List<Map<String, Object>>> chartInfo(String startDate, String endDate);

	/**
	 * 获取运行监控信息
	 * @return
	 */
	ReturnT<Map<String, Object>> monitorInfo();

	/**
	 * 重启全部任务
	 * @return
	 */
	ReturnT<String> restartAll();

	/**
	 * 停止全部任务
	 * @return
	 */
	ReturnT<String> stopAll();

	/**
	 * 获取Job类型统计信息
	 * @return
	 */
	ReturnT<List<Map<String, Object>>> monitorJobTypeInfo();

	/**
	 * 获取JOB执行情况
	 * @return
	 */
	ReturnT<List<Map<String, Object>>> monitorJobExecInfo();
}
