package com.selegant.kettle.handler;

import com.alibaba.fastjson.JSONObject;
import com.selegant.kettle.model.KettleParams;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value = "kettleJobHandler")
@Component
@Slf4j
public class KettleJobHandler extends BaseJobHandler {
    private Logger logger = LoggerFactory.getLogger(KettleJobHandler.class);

    @Autowired
    KettleDatabaseRepository kettleDatabaseRepository;

    @Override
    public ReturnT<String> execute(String params) {
        try {
            KettleParams kettleParams = JSONObject.parseObject(params,KettleParams.class);
            RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory(kettleParams.getObjectDirectory());
            JobMeta jobMeta = kettleDatabaseRepository.loadJob(kettleParams.getObjectName(),directory,new ProgressNullMonitorListener(),null );
            Job job = new Job(kettleDatabaseRepository,jobMeta);
            job.setLogLevel(getLogLevel(kettleParams.getLogLevel()==null?3:kettleParams.getLogLevel()));
            job.start();
            job.waitUntilFinished();
            XxlJobLogger.log("日志等级:"+getLogLevel(kettleParams.getLogLevel()==null?3:kettleParams.getLogLevel()));
            LoggingBuffer appender = KettleLogStore.getAppender();
            String logChannelId = job.getLogChannelId();
            XxlJobLogger.log("日志内容:\n"+appender.getBuffer(logChannelId, true).toString());
            if(job.getErrors()>0){
                return FAIL;
            }
        } catch (KettleException e) {
            logger.error(e.getMessage(),e);
            XxlJobLogger.log("错误信息:"+e.getMessage());
            XxlJobLogger.log("错误具体信息:"+e);
            return FAIL;
        }
        return SUCCESS;
    }

}
