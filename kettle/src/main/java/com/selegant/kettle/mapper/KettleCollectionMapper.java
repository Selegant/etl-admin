package com.selegant.kettle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.KettleCollection;

import java.util.Map;

public interface KettleCollectionMapper extends BaseMapper<KettleCollection> {

    void truncateCollection();

    void deleteToDayCollection();

    Map<String,Object> statisticalDataVolume();
}
