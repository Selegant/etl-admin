package com.selegant.kettle.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.kettle.mapper.TableDescMapper;
import com.selegant.kettle.model.TableDesc;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class TableDescService extends ServiceImpl<TableDescMapper, TableDesc> {


    /**
     * 获取表的中文的描述
     * @param jobDesc 表的描述
     * @param objectType Kettle资源类型 1=任务 2=转换
     * @return
     */
    public String getTableDesc(String jobDesc,Integer objectType){
        if (StrUtil.isBlank(jobDesc) || Objects.isNull(objectType)){
            return StrUtil.EMPTY;
        }
        String tail;
        LambdaQueryChainWrapper<TableDesc> queryChainWrapper = lambdaQuery();
        if (!objectType.equals(BigDecimal.ONE.intValue())){
            queryChainWrapper = queryChainWrapper.eq(TableDesc::getTableName, jobDesc.toLowerCase());
            tail = "的任务";
        }else {
            String trans = jobDesc.toLowerCase().replace("trans_", "v_");
            queryChainWrapper = queryChainWrapper.eq(TableDesc::getTableName,trans).or().eq(TableDesc::getTableName,trans.replace("v_",StrUtil.EMPTY));
            tail = "的转换";
        }
        List<TableDesc> list = queryChainWrapper.list();
        if (CollUtil.isEmpty(list)){
            return StrUtil.EMPTY;
        }
        return list.get(BigDecimal.ZERO.intValue()).getDesc().concat(tail);
    }
}
