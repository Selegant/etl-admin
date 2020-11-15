package com.selegant.web.controller.kettle;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.selegant.kettle.response.PageInfoResponse;
import com.selegant.kettle.service.CollectTimeService;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author selegant
 */
@Controller
@RequestMapping("collectTime")
public class CollectTimeController {

    final
    CollectTimeService collectTimeService;

    public CollectTimeController(CollectTimeService collectTimeService) {
        this.collectTimeService = collectTimeService;
    }

    @RequestMapping("pageList")
    @ResponseBody
    public PageInfoResponse pageList(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                     @RequestParam(required = false, defaultValue = "10") int pageSize,
                                     String sortField,String sortOrder,String viewName ) {
        return collectTimeService.pageList(pageNo,pageSize,sortField,sortOrder,viewName);
    }


    @RequestMapping("/batchUpdateCollectTime")
    @ResponseBody
    public ReturnT<String> batchUpdateCollectTime(@RequestBody Map<String,String> params) {
        String jobNames = params.get("jobNames");
        String collectTime = params.get("collectTime");
        collectTime = DateUtil.format(DateUtil.parse(collectTime,"yyyy-MM-dd HH"), DatePattern.NORM_DATETIME_FORMAT);
        return collectTimeService.batchUpdateCollectTime(jobNames,collectTime);
    }


}
