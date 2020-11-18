package com.selegant.xxljob.dao;

import com.selegant.xxljob.core.model.XxlJobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * job info
 * @author xuxueli 2016-1-12 18:03:45
 */
@Mapper
public interface XxlJobInfoDao {

	public List<XxlJobInfo> pageList(@Param("offset") int offset,
                                     @Param("pagesize") int pagesize,
                                     @Param("jobGroup") int jobGroup,
                                     @Param("triggerStatus") int triggerStatus,
                                     @Param("jobDesc") String jobDesc,
                                     @Param("executorHandler") String executorHandler,
                                     @Param("author") String author,
									 @Param("objectType") String objectType,
									 @Param("cron") String cron,
									 @Param("sortField")String sortField,
									 @Param("sortOrder")String sortOrder);
	public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("jobGroup") int jobGroup,
                             @Param("triggerStatus") int triggerStatus,
                             @Param("jobDesc") String jobDesc,
                             @Param("executorHandler") String executorHandler,
                             @Param("author") String author,
							 @Param("objectType") String objectType,
							 @Param("cron") String cron);

	public int save(XxlJobInfo info);

	public XxlJobInfo loadById(@Param("id") int id);

	public int update(XxlJobInfo xxlJobInfo);

	public int delete(@Param("id") long id);

	public List<XxlJobInfo> getJobsByGroup(@Param("jobGroup") int jobGroup);

	public int findAllCount();

	public int findTypeCount(@Param("objectType") int objectType);

	public List<XxlJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize);

	public int scheduleUpdate(XxlJobInfo xxlJobInfo);

	List<XxlJobInfo> getJobs();


}
