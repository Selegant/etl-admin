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
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KettleJobHandler extends BaseJobHandler {

    private Logger logger = LoggerFactory.getLogger(KettleJobHandler.class);

    @Autowired
    KettleInit kettleInit;

    @Autowired
    ThreadPoolTaskExecutor jobTaskExecutor;

    /**
     * 此处任务放到线程池执行 主要原因为如果不分线程执行 Kettle则无法区分每次执行任务的日志 会获取到任务执行的所有历史日志
     * @param params
     * @return
     */
    @XxlJob(value = "kettleJobHandler")
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
            //重新加载一下资源库防止资源变换 也为后期切换资源库作准备
            KettleDatabaseRepository kettleDatabaseRepository = kettleInit.loadKettleDatabaseRepository();
            KettleParams kettleParams = JSONObject.parseObject(params, KettleParams.class);
            RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory(kettleParams.getObjectDirectory());
            JobMeta jobMeta = kettleDatabaseRepository.loadJob(kettleParams.getObjectName(), directory, new ProgressNullMonitorListener(), null);
            Job job = new Job(kettleDatabaseRepository, jobMeta,new LoggingObject(this));
            job.setDaemon(true);
            job.setLogLevel(getLogLevel(kettleParams.getLogLevel() == null ? 4 : kettleParams.getLogLevel()));
            try {
                job.start();
            } catch (Exception e) {
                XxlJobLogger.log("运行错误信息:" + e.getMessage());
                XxlJobLogger.log("运行具体信息:" + e);
            }
            job.waitUntilFinished();
            XxlJobLogger.log("日志等级:" + getLogLevel(kettleParams.getLogLevel() == null ? 4 : kettleParams.getLogLevel()));
            LoggingBuffer appender = KettleLogStore.getAppender();
            String logChannelId = job.getLogChannelId();
            log.info("logChannelId"+logChannelId);
            XxlJobLogger.log("日志内容:\n" + appender.getBuffer(logChannelId, true).toString());
            if (job.getErrors() > 0) {
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
