package com.selegant.kettle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.KettleCollection;

public interface KettleCollectionMapper extends BaseMapper<KettleCollection> {

    void truncateCollection();

    void deleteToDayCollection();
}
