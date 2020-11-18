package com.selegant.kettle.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.selegant.kettle.mapper.XxlJobLogMapper;
import com.selegant.kettle.model.XxlJobLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class KettleLogHandler extends BaseJobHandler {

    private Logger logger = LoggerFactory.getLogger(KettleLogHandler.class);


    private static final int OVERTIME = 600;

    @Autowired
    XxlJobLogMapper xxlJobLogMapper;

    /**
     * 此处任务放到线程池执行 主要原因为如果不分线程执行 Kettle则无法区分每次执行任务的日志 会获取到任务执行的所有历史日志
     * @param params
     * @return
     */
    @XxlJob(value = "kettleLogHandler")
    @Override
    public ReturnT<String> execute(String params) {
        JSONObject paramsObject = JSON.parseObject(params);
        int overtime = paramsObject.getInteger("overtime");
        try{
            QueryWrapper<XxlJobLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("handle_code",0);
            List<XxlJobLog> logList = xxlJobLogMapper.selectList(queryWrapper);
            Date now = new Date();
            logList.forEach(xxlJobLog -> {
                if(DateUtil.between(xxlJobLog.getTriggerTime(),now, DateUnit.MINUTE)>overtime){
                    XxlJobLogger.log("超时任务ID:"+xxlJobLog.getJobId());
                    XxlJobLogger.log("超时任务执行时间:"+DateUtil.format(xxlJobLog.getTriggerTime(), DatePattern.NORM_DATETIME_FORMAT));
                    XxlJobLogger.log("设置超时时间:"+now);
                    xxlJobLog.setHandleCode(OVERTIME);
                    xxlJobLog.setHandleTime(now);
                    xxlJobLogMapper.updateById(xxlJobLog);
                }
            });
        }catch (Exception e){
            XxlJobLogger.log(e.getMessage());
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }

}
