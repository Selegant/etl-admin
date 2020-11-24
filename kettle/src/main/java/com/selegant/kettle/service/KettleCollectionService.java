package com.selegant.kettle.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.kettle.common.ResultResponse;
import com.selegant.kettle.common.ResultUtils;
import com.selegant.kettle.mapper.KettleCollectionMapper;
import com.selegant.kettle.model.KettleCollection;
import org.springframework.stereotype.Service;

/**
 * @author selegant
 */
@Service
public class KettleCollectionService extends ServiceImpl<KettleCollectionMapper, KettleCollection> {

    private final KettleCollectionMapper kettleCollectionMapper;

    public KettleCollectionService(KettleCollectionMapper kettleCollectionMapper) {
        this.kettleCollectionMapper = kettleCollectionMapper;
    }


    public ResultResponse statisticalDataVolume(){
        return ResultUtils.setOk(kettleCollectionMapper.statisticalDataVolume());
    }
}
