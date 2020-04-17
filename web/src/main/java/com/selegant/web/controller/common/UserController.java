package com.selegant.web.controller.common;


import cn.hutool.core.util.StrUtil;
import com.selegant.common.base.Result;
import com.selegant.common.service.UserService;
import com.selegant.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/info")
    public Result info(HttpServletRequest request) {

        return userService.getUserInfo(request.getHeader("Access-Token"));

    }

    @PostMapping("user/changePassword")
    public Result changePassword(HttpServletRequest request) {
        String accessToken = request.getHeader("Access-Token");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if(StrUtil.isBlank(oldPassword)){
            return ResultUtil.setError("原密码不能为空");
        }
        if(StrUtil.isBlank(newPassword)){
            return ResultUtil.setError("新密码不能为空");
        }
        if(StrUtil.isBlank(confirmPassword)){
            return ResultUtil.setError("确认密码不能为空");
        }
        return userService.changePassword(accessToken,oldPassword,newPassword,confirmPassword);
    }
}
