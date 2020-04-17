package com.selegant.web.controller.kettle;

import com.selegant.kettle.response.PageInfoResponse;
import com.selegant.kettle.service.CollectTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
