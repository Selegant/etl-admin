package com.selegant.kettle.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.kettle.mapper.CollectTimeMapper;
import com.selegant.kettle.model.CollectTime;
import com.selegant.kettle.response.PageInfoResponse;
import com.xxl.job.core.biz.model.ReturnT;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author selegant
 */
@Service
public class CollectTimeService extends ServiceImpl<CollectTimeMapper, CollectTime> {

    final
    CollectTimeMapper collectTimeMapper;

    public CollectTimeService(CollectTimeMapper collectTimeMapper) {
        this.collectTimeMapper = collectTimeMapper;
    }

    public PageInfoResponse pageList(int pageNo, int pageSize, String sortField, String sortOrder, String viewName) {
        QueryWrapper<CollectTime> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(viewName)) {
            queryWrapper.like("view_name", viewName);
        }
        if (StrUtil.isNotBlank(sortField)) {
            boolean asc = "ascend".equals(sortOrder);
            queryWrapper.orderBy(true, asc, StrUtil.toUnderlineCase(sortField));
        }
        Page<CollectTime> page = new Page<>(pageNo, pageSize);
        IPage<CollectTime> list = page(page, queryWrapper);

        PageInfoResponse info = new PageInfoResponse();
        info.setData(list.getRecords());
        info.setTotalCount(list.getTotal());
        info.setPageNo(pageNo);
        info.setPageSize(pageSize);

        return info;
    }

    public void syncJobCollectTime(List<RepositoryElementMetaInterface> jobList) {
        QueryWrapper<CollectTime> queryWrapper = new QueryWrapper<>();
        List<CollectTime> collectTimes = collectTimeMapper.selectList(queryWrapper);
        List<CollectTime> syncCollectTimes = new ArrayList<>(16);
        jobList.forEach(job -> {
            CollectTime collectTime = new CollectTime();
            collectTime.setViewName(job.getName());
            collectTime.setCurrentCollectTime(DateUtil.beginOfDay(new Date()));
            collectTime.setViewDesc(job.getName());
            syncCollectTimes.add(collectTime);
        });
        syncCollectTimes.forEach(sync -> {
            if (collectTimes.size() == 0) {
                collectTimeMapper.insert(sync);
            } else {
                if (collectTimes.stream().noneMatch(collectTime -> collectTime.getViewName().equals(sync.getViewName()))) {
                    collectTimeMapper.insert(sync);
                }
            }
        });
    }


    public void truncateCollectTime() {
        QueryWrapper<CollectTime> queryWrapper = new QueryWrapper<>();
        collectTimeMapper.delete(queryWrapper);
    }

    public ReturnT<String> batchUpdateCollectTime(String jobNames,String collectTime) {
        if(jobNames.contains(",")){
            String[] jobList = jobNames.split(",");
            Arrays.stream(jobList).forEach(s ->{
                collectTimeMapper.updateCollectTimeByViewName(s,collectTime);
            });
        }else {
            collectTimeMapper.updateCollectTimeByViewName(jobNames,collectTime);
        }
        return ReturnT.SUCCESS;
    }
}
