package com.selegant.kettle.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.KettleResource;

public interface KettleResourceMapper extends BaseMapper<KettleResource> {

    void  truncateTable();

    int updateByObjectIdAndOrderType(KettleResource kettleResource);
}
