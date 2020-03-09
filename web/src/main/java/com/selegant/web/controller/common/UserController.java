package com.selegant.web.controller.common;


import com.selegant.common.base.Result;
import com.selegant.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/info")
    public Result info(HttpServletRequest request) {

        return userService.getUserInfo(request.getHeader("Access-Token"));

//        UserInfo userInfo = new UserInfo();
//        String uuid = IdUtil.simpleUUID();
//        userInfo.setId(uuid);
//        userInfo.setName("天下第一");
//        userInfo.setUsername("admin");
//        userInfo.setPassword("");
//        userInfo.setAvatar("https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
//        userInfo.setStatus(1);
//        userInfo.setTelephone("1234567");
//        userInfo.setLastLoginIp("27.154.74.117");
//        userInfo.setLastLoginTime(1534837621348L);
//        userInfo.setCreatorId("admin");
//        userInfo.setCreateTime(1497160610259L);
//        userInfo.setDeleted(0);
//        userInfo.setRoleId("admin");
//        userInfo.setLang("zh-CN");
//        userInfo.setToken("4291d7da9005377ec9aec4a71ea837f");
//
//        Map<String,String> headers = new HashMap<>(16);
//        headers.put("Custom-Header",uuid);
//
//        RoleInfo roleInfo = new RoleInfo();
//        roleInfo.setId(uuid);
//        roleInfo.setName("admin");
//        roleInfo.setDescribe("拥有所有权限");
//        roleInfo.setStatus(1);
//        roleInfo.setCreatorId("system");
//        roleInfo.setCreateTime(1497160610259L);
//        roleInfo.setDeleted(0);
//
//
//        List<RoleInfo.PermissionsBean> list = new ArrayList<>();
//
//        RoleInfo.PermissionsBean permissionsBean = new RoleInfo.PermissionsBean();
//        permissionsBean.setRoleId("admin");
//        permissionsBean.setPermissionId("dashboard");
//        permissionsBean.setPermissionName("仪表盘");
//        permissionsBean.setActions("[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]");
//        permissionsBean.setActionList(null);
//        permissionsBean.setDataAccess(null);
//        permissionsBean.setActionEntitySet(null);
//
//        list.add(permissionsBean);
//
//
//        roleInfo.setPermissions(list);
//
//        userInfo.setRole(roleInfo);
//
//        Result<UserInfo> result = new Result<>();
//        result.setData(userInfo);
//        result.setMessage("");
//        result.setCode(200);
//        result.setHeaders(headers);
//        return result;
    }
}
