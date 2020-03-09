package com.selegant.common.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.common.base.Result;
import com.selegant.common.mapper.EtlActionPermissionMapper;
import com.selegant.common.mapper.EtlPermissionMapper;
import com.selegant.common.mapper.EtlRoleMapper;
import com.selegant.common.mapper.EtlUserMapper;
import com.selegant.common.model.EtlRole;
import com.selegant.common.model.EtlUser;
import com.selegant.common.response.RoleInfo;
import com.selegant.common.response.UserInfo;
import com.selegant.common.service.UserService;
import com.selegant.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<EtlUserMapper,EtlUser> implements UserService {

    @Autowired
    EtlPermissionMapper etlPermissionMapper;

    @Autowired
    EtlUserMapper etlUserMapper;

    @Autowired
    EtlRoleMapper etlRoleMapper;

    @Autowired
    EtlActionPermissionMapper etlActionPermissionMapper;

    @Override
    public Result login(Map<String,String> params) {
        String username = params.get("username");
        String password = params.get("password");
        QueryWrapper<EtlUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        userQueryWrapper.eq("password", password);
        EtlUser user = getOne(userQueryWrapper);

        if(ObjectUtil.isNotEmpty(user)){

            String token = IdUtil.fastSimpleUUID();
            user.setAccessToken(token);
            updateById(user);

            UserInfo userInfo = setUserInfo(user);
            userInfo.setToken(token);

            return ResultUtil.setSuccess(userInfo);
        }
        return null;
    }



    @Override
    public Result getUserInfo(String accessToken) {
        if(ObjectUtil.isEmpty(accessToken)){
            return null;
        }
        QueryWrapper<EtlUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("access_token",accessToken);

        EtlUser user = getOne(userQueryWrapper);

        QueryWrapper<EtlRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_id",user.getRoleId());
        EtlRole role = etlRoleMapper.selectOne(roleQueryWrapper);

        UserInfo userInfo = setUserInfo(user);
        userInfo.setToken(accessToken);

        RoleInfo roleInfo  = new RoleInfo();
        roleInfo.setId(role.getRoleId());
        roleInfo.setName(role.getName());
        roleInfo.setDescribe(role.getRoleDesc());
        roleInfo.setStatus(role.getStatus());
        roleInfo.setCreatorId(role.getCreatorId());
//        roleInfo.setCreateTime(role.getCreateTime().getTime());
        roleInfo.setDeleted(role.getDeleted());


        List<Map<String,Object>> permissionList = etlPermissionMapper.getPermissionByRoleId(role.getRoleId());
        List<RoleInfo.PermissionsBean> permissionsBeanList = new ArrayList<>(16);
        permissionList.forEach(permission->{
            RoleInfo.PermissionsBean permissionsBean = new RoleInfo.PermissionsBean();
            permissionsBean.setRoleId(role.getRoleId());
            permissionsBean.setPermissionId(permission.get("permission_id").toString());
            permissionsBean.setPermissionName(permission.get("permission_name").toString());
            permissionsBean.setActionList(null);
            permissionsBean.setDataAccess(null);
            List<Map<String,Object>> actionList = etlPermissionMapper.getActionByPermission(permission.get("permission_id").toString());

            List<RoleInfo.PermissionsBean.ActionEntitySetBean> actionEntitySetBeanList = new ArrayList<>(16);
            actionList.forEach(action->{
                RoleInfo.PermissionsBean.ActionEntitySetBean actionEntitySetBean = new RoleInfo.PermissionsBean.ActionEntitySetBean();
                actionEntitySetBean.setAction(action.get("action_id").toString());
                actionEntitySetBean.setDescribe(action.get("action_desc").toString());
                actionEntitySetBean.setDefaultCheck(BooleanUtil.toBoolean(action.get("default_check").toString()));
                actionEntitySetBeanList.add(actionEntitySetBean);
            });


            permissionsBean.setActions(JSONObject.toJSONString(actionEntitySetBeanList));
            permissionsBean.setActionEntitySet(actionEntitySetBeanList);
            permissionsBeanList.add(permissionsBean);
        });

        roleInfo.setPermissions(permissionsBeanList);


        userInfo.setRole(roleInfo);

        Map<String,String> headers = new HashMap<>(16);
        headers.put("Custom-Header",IdUtil.fastSimpleUUID());

        return ResultUtil.setSuccess(userInfo,headers);
    }


    private UserInfo setUserInfo(EtlUser user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId().toString());
        userInfo.setName(user.getName());
        userInfo.setUsername(user.getUsername());
        userInfo.setPassword(user.getPassword());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setStatus(user.getStatus());
        userInfo.setTelephone(user.getTelephone());
        userInfo.setLastLoginIp(user.getLastLoginIp());
//        userInfo.setLastLoginTime(user.getLastLoginTime().getTime());
        userInfo.setCreatorId(user.getCreatorId());
//        userInfo.setCreateTime(user.getCreateTime().getTime());
        userInfo.setDeleted(user.getDeleted());
        userInfo.setRoleId(user.getRoleId());
        userInfo.setLang("zh-CN");
        return userInfo;
    }

}
