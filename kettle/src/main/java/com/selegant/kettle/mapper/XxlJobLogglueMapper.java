package com.selegant.kettle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.XxlJobLogglue;
import org.apache.ibatis.annotations.Param;

public interface XxlJobLogglueMapper extends BaseMapper<XxlJobLogglue> {

    int deleteByJobId(@Param("jobId") int jobId);
}
