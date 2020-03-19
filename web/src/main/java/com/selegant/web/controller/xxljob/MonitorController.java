package com.selegant.web.controller.xxljob;

import com.selegant.xxljob.core.model.XxlJobInfo;
import com.selegant.xxljob.core.model.XxlJobLog;
import com.selegant.xxljob.dao.XxlJobInfoDao;
import com.selegant.xxljob.dao.XxlJobLogDao;
import com.selegant.xxljob.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("monitor")
public class MonitorController {

    @Resource
    private XxlJobService xxlJobService;

    @Autowired
    private XxlJobLogDao xxlJobLogDao;

    @Autowired
    private XxlJobInfoDao xxlJobInfoDao;

    @RequestMapping("monitorInfo")
    @ResponseBody
    public ReturnT<Map<String, Object>> index() {
        return xxlJobService.monitorInfo();
    }


    @RequestMapping("monitorJobTypeInfo")
    @ResponseBody
    public ReturnT<List<Map<String, Object>>> monitorJobTypeInfo() {
        return xxlJobService.monitorJobTypeInfo();
    }

    @RequestMapping("monitorJobStatusInfo")
    @ResponseBody
    public ReturnT<List<Map<String, Object>>> monitorJobStatusInfo() {
        return xxlJobService.monitorJobStatusInfo();
    }

    @RequestMapping("monitorJobExecInfo")
    @ResponseBody
    public ReturnT<List<Map<String, Object>>> monitorJobExecInfo() {
        return xxlJobService.monitorJobExecInfo();
    }

    @RequestMapping("/monitorTaskExecInfo")
    @ResponseBody
    public ReturnT<Map<String, Object>> monitorTaskExecInfo(String startDate, String endDate, String jobId) {
        return xxlJobService.monitorTaskExecInfo(startDate, endDate, jobId);
    }


    @RequestMapping("monitorPageList")
    @ResponseBody
    public Map<String, Object> monitorPageList(HttpServletRequest request,
                                               @RequestParam(required = false, defaultValue = "0") int start,
                                               @RequestParam(required = false, defaultValue = "10") int length) {

        List<XxlJobInfo> list = xxlJobInfoDao.pageList(start, length, -1, -1, "", "", "",null,null);
        int list_count = xxlJobInfoDao.pageListCount(start, length, -1, -1, "", "", "",null,null);
        List<Map<String,Object>> results = new ArrayList<>();
        list.forEach(s->{
            List<XxlJobLog> logs = xxlJobLogDao.monitorList(s.getId());
            Map<String,Object> map = new HashMap<>();
            if(logs.isEmpty()){
                map.put("id",s.getId());
                map.put("name",s.getJobDesc());
                map.put("count",0);
                map.put("successCount",0);
                map.put("errorCount",0);
            }else {
                long count = logs.size();
                long successCount = logs.stream().filter(l->200==l.getHandleCode()).count();
                long errorCount = logs.size() - successCount;
                map.put("id",s.getId());
                map.put("name",s.getJobDesc());
                map.put("count",count);
                map.put("successCount",successCount);
                map.put("errorCount",errorCount);
            }
            results.add(map);
        });
        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);		// 总记录数
        maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
        maps.put("data", results);  					// 分页列表
        return maps;
    }
}
