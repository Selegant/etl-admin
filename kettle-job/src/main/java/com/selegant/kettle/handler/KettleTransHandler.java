package com.selegant.kettle.handler;

import com.alibaba.fastjson.JSONObject;
import com.selegant.kettle.init.KettleInit;
import com.selegant.kettle.model.KettleParams;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.core.logging.LoggingObject;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KettleTransHandler extends BaseJobHandler {
    private Logger logger = LoggerFactory.getLogger(KettleTransHandler.class);

    @Autowired
    KettleInit kettleInit;

    @Autowired
    ThreadPoolTaskExecutor jobTaskExecutor;

    @XxlJob(value = "kettleTransHandler")
    @Override
    public ReturnT<String> execute(String params) {
        return run(params);
//        Future<ReturnT<String>> future = jobTaskExecutor.submit(() -> run(params));
//        while (true){
//            if(future.isDone()){
//                break;
//            }else {
//                ThreadUtil.safeSleep(10000);
//            }
//        }
//        try {
//            return future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return FAIL;
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            return FAIL;
//        }
    }

    private ReturnT<String> run(String params) {
        try {
            KettleDatabaseRepository kettleDatabaseRepository = kettleInit.loadKettleDatabaseRepository();
            KettleParams kettleParams = JSONObject.parseObject(params, KettleParams.class);
            RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory(kettleParams.getObjectDirectory());
            TransMeta transMeta = kettleDatabaseRepository.loadTransformation(kettleParams.getObjectName(), directory, new ProgressNullMonitorListener(), true, null);
            Trans trans = new Trans(transMeta,new LoggingObject(this));
            trans.setLogLevel(getLogLevel(kettleParams.getLogLevel()));
            try {
                trans.execute(null);
            } catch (Exception e) {
                XxlJobLogger.log("运行错误信息:" + e.getMessage());
                XxlJobLogger.log("运行具体信息:" + e);
            }
            trans.waitUntilFinished();
            XxlJobLogger.log("KETTLE");
            XxlJobLogger.log("KETTLE_TRANS");
            XxlJobLogger.log("TASK_NAME:"+kettleParams.getObjectName());
            XxlJobLogger.log("日志等级:" + getLogLevel(kettleParams.getLogLevel()));
            LoggingBuffer appender = KettleLogStore.getAppender();
            String logChannelId = trans.getLogChannelId();
            log.info("logChannelId"+logChannelId);
            XxlJobLogger.log("日志内容:\n" + appender.getBuffer(logChannelId, true).toString());
            if (trans.getErrors() > 0) {
                return FAIL;
            }
        } catch (KettleException e) {
            logger.error(e.getMessage(), e);
            XxlJobLogger.log("错误信息:" + e.getMessage());
            XxlJobLogger.log("错误具体信息:" + e);
            return FAIL;
        }
        return SUCCESS;
    }

}
