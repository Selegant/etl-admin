package com.selegant.kettle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.CollectTime;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectTimeMapper extends BaseMapper<CollectTime> {

    void updateCollectTimeByViewName(@Param("viewName")String viewName,@Param("collectTime")String collectTime);
}
