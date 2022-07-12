package com.selegant.kettle.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.kettle.mapper.KettleDataStatisticsMapper;
import com.selegant.kettle.model.KettleDataStatistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KettleDataStatisticsService extends ServiceImpl<KettleDataStatisticsMapper, KettleDataStatistics> {

    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateByJobIdAndTime(List<KettleDataStatistics> list) {
        if (CollUtil.isEmpty(list)){
            return true;
        }
        List<String> dbNameList = new ArrayList<>();
        List<String> tableNameList = new ArrayList<>();
        List<Integer> jobIdList = new ArrayList<>();
        List<LocalDateTime> createTimeList = list.stream().map(v->{
            dbNameList.add(v.getDatabaseName());
            tableNameList.add(v.getTableName());
            jobIdList.add(v.getJobId());
            return v.getCreateTime();
        }).collect(Collectors.toList());

        List<KettleDataStatistics> dataStatistics = lambdaQuery().in(KettleDataStatistics::getDatabaseName, dbNameList)
                .in(KettleDataStatistics::getTableName, tableNameList)
                .in(KettleDataStatistics::getJobId, jobIdList)
                .in(KettleDataStatistics::getCreateTime, createTimeList)
                .list();
        if (CollUtil.isEmpty(dataStatistics)){
           return saveBatch(list);
        }
        Map<String, KettleDataStatistics> map = dataStatistics.stream().collect(Collectors.toMap(
                v -> v.getDatabaseName().concat(StrUtil.DASHED).concat(v.getTableName()).concat(StrUtil.DASHED).concat(v.getJobId().toString()).concat(StrUtil.DASHED).concat(v.getCreateTime().toString())
                , v -> v));
        Map<Boolean, List<KettleDataStatistics>> saveOrUpdate = list.stream()
                .collect(Collectors.groupingBy(v -> map.containsKey(v.getDatabaseName().concat(StrUtil.DASHED).concat(v.getTableName()).concat(StrUtil.DASHED).concat(v.getJobId().toString()).concat(StrUtil.DASHED).concat(v.getCreateTime().toString()))));
        list.clear();
        boolean result = true;
        List<KettleDataStatistics> saveList = saveOrUpdate.get(false);
        if (CollUtil.isNotEmpty(saveList)){
           result =  saveBatch(saveList);
        }
        List<KettleDataStatistics> updateList = saveOrUpdate.get(true);
        if (CollUtil.isNotEmpty(updateList)){
            List<KettleDataStatistics> needUpdateList = updateList.stream().filter(v -> {
                KettleDataStatistics kettleDataStatistics = map.get(v.getDatabaseName().concat(StrUtil.DASHED).concat(v.getTableName()).concat(StrUtil.DASHED).concat(v.getJobId().toString()).concat(StrUtil.DASHED).concat(v.getCreateTime().toString()));
                v.setId(kettleDataStatistics.getId());
                return !v.equals(kettleDataStatistics);
            }).collect(Collectors.toList());
            result = updateBatchById(needUpdateList);
        }
        return result;
    }
}
