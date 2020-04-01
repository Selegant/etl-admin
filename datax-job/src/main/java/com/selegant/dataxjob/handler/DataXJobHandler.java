package com.selegant.dataxjob.handler;

import cn.hutool.core.io.IoUtil;
import com.alibaba.datax.core.Engine;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalTime;

import static com.xxl.job.core.handler.IJobHandler.SUCCESS;


@Component
@Slf4j
public class DataXJobHandler  {
    private Logger logger = LoggerFactory.getLogger(DataXJobHandler.class);

    @XxlJob(value = "dataxJobHandler")
    public ReturnT<String> execute(String params) {
        String[] dataxArgs = {"-job", "/Users/selegant/Downloads/datax/job/test.json", "-mode", "standalone", "-jobid", "-1"};
        try {
            System.setProperty("datax.home", "/Users/selegant/Downloads/datax");
            System.setProperty("now", LocalTime.now().toString());// 替换job中的占位符
            Engine.entry(dataxArgs);
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return SUCCESS;
    }

}
