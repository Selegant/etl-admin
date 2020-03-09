package com.selegant.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.common.model.EtlPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface EtlPermissionMapper extends BaseMapper<EtlPermission> {

    List<Map<String,Object>> getActionByPermission(@Param(value = "permission_id")String permissionId);


    List<Map<String,Object>> getPermissionByRoleId(@Param(value = "role_id")String roleId);
}
