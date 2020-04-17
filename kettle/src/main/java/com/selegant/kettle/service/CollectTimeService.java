package com.selegant.kettle.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.kettle.mapper.CollectTimeMapper;
import com.selegant.kettle.model.CollectTime;
import com.selegant.kettle.response.PageInfoResponse;
import org.springframework.stereotype.Service;

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

    public PageInfoResponse pageList(int pageNo, int pageSize,String sortField,String sortOrder,String viewName) {
        QueryWrapper<CollectTime> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(viewName)){
            queryWrapper.like("view_name",viewName);
        }
        if(StrUtil.isNotBlank(sortField)){
            boolean asc = "ascend".equals(sortOrder);
            queryWrapper.orderBy(true,asc, StrUtil.toUnderlineCase(sortField));
        }
        Page<CollectTime> page = new Page<>(pageNo, pageSize);
        IPage<CollectTime> list = page(page,queryWrapper);

        PageInfoResponse info = new PageInfoResponse();
        info.setData(list.getRecords());
        info.setTotalCount(list.getTotal());
        info.setPageNo(pageNo);
        info.setPageSize(pageSize);

        return info;
    }
}
