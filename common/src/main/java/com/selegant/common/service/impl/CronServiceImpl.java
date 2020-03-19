package com.selegant.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.common.mapper.EtlCronMapper;
import com.selegant.common.model.EtlCron;
import com.selegant.common.service.CronService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronServiceImpl extends ServiceImpl<EtlCronMapper, EtlCron> implements CronService {
    @Override
    public List<EtlCron> getCron() {
        return list();
    }
}
