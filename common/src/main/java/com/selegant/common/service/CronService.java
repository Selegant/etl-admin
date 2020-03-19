package com.selegant.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.selegant.common.model.EtlCron;

import java.util.List;

public interface CronService extends IService<EtlCron> {

    List<EtlCron> getCron();
}
