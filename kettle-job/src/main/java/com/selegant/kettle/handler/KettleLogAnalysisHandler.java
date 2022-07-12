package com.selegant.kettle.handler;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.selegant.kettle.mapper.KettleCollectionMapper;
import com.selegant.kettle.model.KettleCollection;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.selegant.kettle.constant.KettleLogConstant.*;

/**
 * @author selegant
 */
@Component
@Slf4j
public class KettleLogAnalysisHandler extends BaseJobHandler {

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Autowired
    KettleCollectionMapper kettleCollectionMapper;




    @XxlJob(value = "kettleLogAnalysisHandler")
    @Override
    public ReturnT<String> execute(String params) {
        JSONObject paramsObj = JSON.parseObject(params);
        String type = paramsObj.getString("type");
        try {
            if (ALL.equals(type)) {
                XxlJobLogger.log("-------------------分析全部日志--------------------");
                XxlJobLogger.log("日志路径:" + logPath);
                analysisAll();
            }
            if (TODAY.equals(type)) {
                XxlJobLogger.log("-------------------分析今日日志--------------------");
                XxlJobLogger.log("日志路径:" + logPath);
                analysisToday();
            }
        } catch (Exception e) {
            XxlJobLogger.log("-------------------分析失败--------------------");
            XxlJobLogger.log(e.getMessage());
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("-------------------分析完成--------------------");
        return ReturnT.SUCCESS;
    }

    public void analysisAll() {
        List<File> fileList = FileUtil.loopFiles(logPath);
        List<KettleCollection> collections = new ArrayList<>();
        XxlJobLogger.log("日志数量:" + fileList.size());
        fileList.forEach(file -> {
            XxlJobLogger.log("日志地址:" + file.getAbsolutePath());
            KettleCollection collection = analysisContent(file);
            if (!Objects.isNull(collection)) {
                collections.add(collection);
            } else {
                XxlJobLogger.log("日志不符合规范");
            }
        });
        kettleCollectionMapper.truncateCollection();
        collections.forEach(collection -> kettleCollectionMapper.insert(collection));
        XxlJobLogger.log(JSONObject.toJSONString(collections));
    }

    public void analysisToday() {
        List<File> fileList = FileUtil.loopFiles(logPath);
        fileList = fileList.stream().filter(file -> file.getAbsolutePath().contains(DateUtil.format(new Date(), DatePattern.NORM_DATE_FORMAT))).collect(Collectors.toList());
        XxlJobLogger.log("日志数量:" + fileList.size());
        List<KettleCollection> collections = new ArrayList<>();
        fileList.forEach(file -> {
            XxlJobLogger.log("日志地址:" + file.getAbsolutePath());
            KettleCollection collection = analysisContent(file);
            if (!Objects.isNull(collection)) {
                collections.add(collection);
            } else {
                XxlJobLogger.log("日志不符合规范");
            }
        });
        kettleCollectionMapper.deleteToDayCollection();
        collections.forEach(collection -> kettleCollectionMapper.insert(collection));
        XxlJobLogger.log(JSONObject.toJSONString(collections));
    }


    public KettleCollection analysisContent(File file) {
        KettleCollection collection = new KettleCollection();
        List<String> content = FileUtil.readLines(file, "UTF-8");
        long count = content.stream().filter(line -> line.contains(KETTLE)).count();
        if (count == 0) {
            return null;
        }
        collection.setLogId(Integer.parseInt(file.getName().split(".log")[0]));
        if (content.stream().anyMatch(line -> line.contains(KETTLE_JOB))) {
            collection.setJobType(2);
        }
        if (content.stream().anyMatch(line -> line.contains(KETTLE_TRANS))) {
            collection.setJobType(1);
        }
        if (content.size() - 5 > 0) {
            String collectExecuteTime = content.get(content.size() - 5);
            if (StrUtil.isNotBlank(collectExecuteTime) && collectExecuteTime.contains("/")) {
                collection.setCollectExecuteTime(DateUtil.parse(collectExecuteTime.substring(0, collectExecuteTime.indexOf(" - ")), "yyyy/MM/dd HH:mm:ss"));
            }
        }
        String viewName = "";
        try {
            viewName = content.stream().filter(line -> line.contains(TASK_NAME)).findFirst().orElse(TASK_NAME).split(TASK_NAME)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        collection.setViewName(viewName);
        if (content.stream().anyMatch(line -> line.contains(KETTLE_EXCEPTION))) {
            collection.setErrorFlag(1);
        }
        if (content.stream().anyMatch(line -> line.contains(KETTLE_COLLECT_START_TIME_DESC))) {
            String collectContent = content.stream().filter(line -> line.contains(KETTLE_COLLECT_START_TIME_DESC)).findFirst().orElse("");
            String collectTime = collectContent.substring(collectContent.indexOf("[") + 1, collectContent.indexOf("]"));
            collection.setCollectStartTime(DateUtil.parseDate(collectTime));
        }
        if (content.stream().anyMatch(line -> line.contains(KETTLE_COLLECT_END_TIME_DESC))) {
            String collectContent = content.stream().filter(line -> line.contains(KETTLE_COLLECT_END_TIME_DESC)).findFirst().orElse("");
            String collectTime = collectContent.substring(collectContent.indexOf("[") + 1, collectContent.indexOf("]"));
            try{
                collection.setCollectEndTime(DateUtil.parse(collectTime, "yyyy/MM/dd HH:mm:ss"));
            }catch (DateException e){
                try {
                    collection.setCollectEndTime(DateUtil.parse(collectTime, "yyyy-MM-dd HH:mm:ss"));
                }catch (DateException m){
                    if (collectTime.length() == 10) {
                        collection.setCollectEndTime(DateUtil.parse(collectTime, "yyyy-MM-dd"));
                    }
                    if (collectTime.length() == 13) {
                        collection.setCollectEndTime(DateUtil.parse(collectTime, "yyyy-MM-dd HH"));
                    }
                    if (collectTime.length() == 16) {
                        collection.setCollectEndTime(DateUtil.parse(collectTime, "yyyy-MM-dd HH:mm"));
                    }
                }
            }
        }
        if (content.stream().anyMatch(line -> line.contains(KETTLE_COLLECT_DESC))) {
            String collectContent = content.stream().filter(line -> line.contains(KETTLE_COLLECT_DESC)).findFirst().get();
            collectContent = collectContent.substring(collectContent.indexOf("(") + 1, collectContent.indexOf(")"));
            log.info(collectContent);
            String[] strings = collectContent.split(", ");
            collection.setInputNum(Integer.parseInt(strings[0].split("I=")[1]));
            collection.setOutputNum(Integer.parseInt(strings[1].split("O=")[1]));
            collection.setReadNum(Integer.parseInt(strings[2].split("R=")[1]));
            collection.setWriteNum(Integer.parseInt(strings[3].split("W=")[1]));
            collection.setUpdateNum(Integer.parseInt(strings[4].split("U=")[1]));
            collection.setErrorNum(Integer.parseInt(strings[5].split("E=")[1]));
        }
        collection.setCreateTime(new Date());
        collection.setUpdateTime(new Date());
        return collection;
    }
}
