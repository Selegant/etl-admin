package com.selegant.web.controller.common;

import com.selegant.common.base.Result;
import com.selegant.common.service.CronService;
import com.selegant.common.service.UserService;
import com.selegant.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("cron")
public class CronController {

    @Autowired
    CronService cronService;

    @GetMapping("getCron")
    public Result getCron() {
        return ResultUtil.setSuccess(cronService.getCron());
    }

}
