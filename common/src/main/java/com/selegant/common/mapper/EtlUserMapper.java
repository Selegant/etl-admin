package com.selegant.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.common.model.EtlUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EtlUserMapper extends BaseMapper<EtlUser> {
}
