package com.selegant.web.controller.common;

import com.selegant.common.base.Result;
import com.selegant.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class LoginController {

    @Autowired
    UserService userService;


    @PostMapping("auth/login")
    public Result login(@RequestBody Map<String,String> params) {
       return userService.login(params);
    }


    @PostMapping("auth/logout")
    public Result logout(HttpServletRequest request) {
        return null;
    }


}
