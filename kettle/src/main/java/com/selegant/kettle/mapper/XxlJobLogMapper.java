package com.selegant.kettle.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.XxlJobLog;
import org.apache.ibatis.annotations.Param;

public interface XxlJobLogMapper extends BaseMapper<XxlJobLog> {
    int deleteByJobId(@Param("jobId") int jobId);
}
