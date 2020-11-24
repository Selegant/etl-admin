package com.selegant.xxljob.dao;

import com.selegant.xxljob.core.model.XxlJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * job log
 * @author xuxueli 2016-1-12 18:03:06
 */
@Mapper
public interface XxlJobLogDao {

	// exist jobId not use jobGroup, not exist use jobGroup
	public List<XxlJobLog> pageList(@Param("offset") int offset,
                                    @Param("pagesize") int pagesize,
                                    @Param("jobGroup") int jobGroup,
                                    @Param("jobId") int jobId,
                                    @Param("triggerTimeStart") Date triggerTimeStart,
                                    @Param("triggerTimeEnd") Date triggerTimeEnd,
                                    @Param("logStatus") int logStatus,
									@Param("readMark") int readMark,
									@Param("jobType") int jobType);
	public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("jobGroup") int jobGroup,
                             @Param("jobId") int jobId,
                             @Param("triggerTimeStart") Date triggerTimeStart,
                             @Param("triggerTimeEnd") Date triggerTimeEnd,
                             @Param("logStatus") int logStatus,
							 @Param("readMark") int readMark,
							 @Param("jobType") int jobType);

	public XxlJobLog load(@Param("id") long id);

	public long save(XxlJobLog xxlJobLog);

	public int updateTriggerInfo(XxlJobLog xxlJobLog);

	public int updateHandleInfo(XxlJobLog xxlJobLog);

	int updateReadMark(XxlJobLog xxlJobLog);

	public int delete(@Param("jobId") int jobId);

	public Map<String, Object> findLogReport(@Param("from") Date from,
                                             @Param("to") Date to);

	public List<Long> findClearLogIds(@Param("jobGroup") int jobGroup,
                                      @Param("jobId") int jobId,
                                      @Param("clearBeforeTime") Date clearBeforeTime,
                                      @Param("clearBeforeNum") int clearBeforeNum,
                                      @Param("pagesize") int pagesize);
	public int clearLog(@Param("logIds") List<Long> logIds);

	public List<Long> findFailJobLogIds(@Param("pagesize") int pagesize);

	public int updateAlarmStatus(@Param("logId") long logId,
                                 @Param("oldAlarmStatus") int oldAlarmStatus,
                                 @Param("newAlarmStatus") int newAlarmStatus);


	public List<Map<String,Object>> monitorPageList(@Param("offset") int offset,
                                                    @Param("pagesize") int pagesize,
                                                    @Param("triggerTimeStart") Date triggerTimeStart,
                                                    @Param("triggerTimeEnd") Date triggerTimeEnd,
                                                    @Param("logStatus") int logStatus);

	public int monitorPageListCount(@Param("offset") int offset,
                                    @Param("pagesize") int pagesize,
                                    @Param("triggerTimeStart") Date triggerTimeStart,
                                    @Param("triggerTimeEnd") Date triggerTimeEnd,
                                    @Param("logStatus") int logStatus);

	public List<XxlJobLog> monitorList(@Param("jobId") int jobId);

	public List<XxlJobLog> getMonitorLogInfo(@Param("start")Date start,@Param("end")Date end);

	int getUnReadCount();

	int count();
}
