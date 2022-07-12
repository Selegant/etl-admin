package com.selegant.kettle.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.selegant.kettle.model.KettleDataStatistics;
import com.selegant.kettle.model.XxlJobLog;
import com.selegant.kettle.service.KettleDataStatisticsService;
import com.selegant.kettle.service.XxlJobLogService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.selegant.kettle.constant.KettleLogConstant.*;

/**
 * @author 薛云腾
 */
@Component
@Slf4j
public class WaterfallStaticsHandler extends IJobHandler {

    public static void main(String[] args) {
        BigDecimal num = BigDecimal.ONE;
        for (int i = 0; i < 10; i++) {
            num = num.multiply(new BigDecimal(1.01));
        }
        System.out.println("num = " + num);
        final BigDecimal multiply = BigDecimal.ONE.multiply(new BigDecimal(1.0041)).multiply(new BigDecimal(1.0069)).multiply(new BigDecimal(1.0381)).multiply(new BigDecimal(1.0058));
        System.out.println("multiply = " + multiply);
    }

    @Value("${kettle.waterfall-statics}")
    private Boolean isOpen;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Autowired
    private KettleDataStatisticsService kettleDataStatisticsService;

    @Autowired
    private XxlJobLogService xxlJobLogService;

    @XxlJob(value = "waterfallStaticsHandler")
    @Override
    public ReturnT<String> execute(String params) {
        if (isOpen){
            TypeEnums type = matchTypeEnums(params);
            if (type.equals(TypeEnums.ALL)){
                all();
            }else if (type.equals(TypeEnums.DAY)){
                day();
            }
        }
        return ReturnT.SUCCESS;
    }

    public TypeEnums matchTypeEnums(String params){
        if (StrUtil.isNotBlank(params)){
            Param param = JSON.parseObject(params, Param.class);
            if (Objects.nonNull(param) && StrUtil.isNotBlank(param.getType())){
                return TypeEnums.valueOf(param.getType());
            }
        }
        return kettleDataStatisticsService.count() > BigDecimal.ZERO.intValue() ? TypeEnums.DAY : TypeEnums.ALL;
    }

    public void all(){
        List<KettleDataStatistics> kettleCollections = get(true);
        collection(kettleCollections);
    }

    public void day(){
        List<KettleDataStatistics> kettleCollections = get(false);
        collection(kettleCollections);
    }

    public void collection(List<KettleDataStatistics> kettleCollections){
        boolean result = kettleDataStatisticsService.saveOrUpdateByJobIdAndTime(kettleCollections);
        if (result){
            XxlJobLogger.log("同步成功");
        }else {
            XxlJobLogger.log("同步失败");
        }
    }

    public List<KettleDataStatistics> get(boolean isAll){
        List<File> fileList = FileUtil.loopFiles(logPath);
        if (!isAll){
            fileList = fileList.stream().filter(file -> file.getAbsolutePath().contains(DateUtil.format(new Date(), DatePattern.NORM_DATE_FORMAT))).collect(Collectors.toList());
        }
        if (CollUtil.isEmpty(fileList)){
            return Collections.emptyList();
        }
        XxlJobLogger.log("日志数量:" + fileList.size());
        Map<Long, Integer> map = new HashMap<>(BigDecimal.TEN.intValue());
        List<Integer> collect = fileList.stream().map(file -> Integer.parseInt(file.getName().replace(LOG_TAIL, StrUtil.EMPTY))).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)){
            map.putAll(xxlJobLogService.lambdaQuery()
                    .select(XxlJobLog::getId, XxlJobLog::getJobId)
                    .in(XxlJobLog::getId, collect)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(XxlJobLog::getId, XxlJobLog::getJobId)));
        }
        return fileList.stream().map(file -> {
            XxlJobLogger.log("日志地址:" + file.getAbsolutePath());
            KettleDataStatistics collection = analysisContent(file);
            if (Objects.isNull(collection)) {
                XxlJobLogger.log("日志不符合规范");
            }
            if (CollUtil.isNotEmpty(map)){
                Integer logId = Integer.parseInt(file.getName().replace(LOG_TAIL, StrUtil.EMPTY));
                Integer jobId = map.get(logId);
                if (Objects.nonNull(jobId)){
                    collection.setJobId(jobId);
                }
            }
            return collection;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public KettleDataStatistics analysisContent(File file) {
        KettleDataStatistics kettleDataStatistics = new KettleDataStatistics();
        List<String> content = FileUtil.readLines(file, "UTF-8");
        //kettle的日志标识
        AtomicBoolean isKettle = new AtomicBoolean(false);
        content.forEach(line->{
            if (!isKettle.get()){
                isKettle.set(line.contains(KETTLE));
            }
            if (line.contains(JOB_START_TARGET)){
                String dateStr = line.substring(BigDecimal.ZERO.intValue(), 19);
                LocalDateTime dateTime = LocalDateTimeUtil.parse(dateStr, "yyyy/MM/dd HH:mm:ss");
                kettleDataStatistics.setStatisticsTime(dateTime.toLocalDate());
                kettleDataStatistics.setCreateTime(dateTime);
                kettleDataStatistics.setUpdateTime(dateTime);
            }else if (line.contains(KETTLE_COLLECT_DESC)){
                /**
                 * 2022/07/07 09:32:31 - 插入 / 更新.0 - 完成处理 (I=16999, O=0, R=16999, W=16999, U=38, E=0)
                 * 提取 W=16999 和 U=38
                 */
                Integer writeTotalNum = BigDecimal.ZERO.intValue();
                Integer updateNum = BigDecimal.ZERO.intValue();
                for (String dataResult : line.substring(line.indexOf("(") + BigDecimal.ONE.intValue(), line.indexOf(")")).split(StrUtil.COMMA)) {
                    if (dataResult.contains(WRITE_TARGET)){
                        writeTotalNum = Integer.parseInt(dataResult.replace(WRITE_TARGET,StrUtil.EMPTY));
                    }else if (dataResult.contains(UPDATE_TARGET)){
                        updateNum = Integer.parseInt(dataResult.replace(UPDATE_TARGET,StrUtil.EMPTY));
                    }
                }
                kettleDataStatistics.setDataUpdateNum(updateNum);
                kettleDataStatistics.setDataInsertNum(writeTotalNum - updateNum);
            }else if (line.contains(INSERT_SQL_TARGET)){
                /**
                 * 2022/07/07 09:32:17 - nbchr - INSERT INTO c_warn (prison_code, in_prison_status_code, crime_no, warn_level, warner_code, warner_name, warn_date, collect_serial_no, start_time, end_time) VALUES ( ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)
                 * 提取 nbchr 和 c_warn
                 */
                String[] split = line.split(StrUtil.SPACE.concat(StrUtil.DASHED).concat(StrUtil.SPACE));
                kettleDataStatistics.setDatabaseName(split[BigDecimal.ONE.intValue()]);
                String sql = split[2].replace(INSERT_SQL_TARGET, StrUtil.EMPTY);
                kettleDataStatistics.setTableName(sql.substring(BigDecimal.ZERO.intValue(),sql.indexOf(" (")));
            }
        });
        if (!isKettle.get()){
            return null;
        }
        return kettleDataStatistics;
    }

    public enum TypeEnums{
        ALL,DAY
    }

    @Data
    @NoArgsConstructor
    public static class Param{
        private String type;
    }
}
