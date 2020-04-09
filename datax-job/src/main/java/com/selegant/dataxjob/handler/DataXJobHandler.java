package com.selegant.dataxjob.handler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.datax.core.Engine;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalTime;

import static com.xxl.job.core.handler.IJobHandler.FAIL;
import static com.xxl.job.core.handler.IJobHandler.SUCCESS;


@Component
@Slf4j
public class DataXJobHandler  {
    private Logger logger = LoggerFactory.getLogger(DataXJobHandler.class);

    @XxlJob(value = "dataxJobHandler")
    public ReturnT<String> execute(String params) {
        String dataXPath = "/Users/selegant/Downloads/ETL/datax";
        String tmpPath = dataXPath + "/tmp";
        String name = tmpPath + "/" + UUID.fastUUID() + ".json";
        FileUtil.appendString(params,name, "UTF-8");
        String[] dataxArgs = {"-job", name, "-mode", "standalone", "-jobid", "-1"};
        try {
            System.setProperty("datax.home", dataXPath);
            System.setProperty("now", LocalTime.now().toString());// 替换job中的占位符
            Engine.entry(dataxArgs);
        } catch (Throwable e) {
            e.printStackTrace();
            XxlJobLogger.log("错误信息:" + e.getMessage());
            XxlJobLogger.log("错误具体信息:" + e);
            return FAIL;
        }
        FileUtil.del(new File(name));
        return SUCCESS;
    }

}
