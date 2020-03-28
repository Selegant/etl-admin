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

    }
}
