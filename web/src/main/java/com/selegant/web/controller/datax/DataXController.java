package com.selegant.web.controller.datax;

import com.selegant.datax.base.Result;
import com.selegant.datax.model.DataxDatasource;
import com.selegant.datax.request.GenerateDataXRequest;
import com.selegant.datax.response.PageInfoResponse;
import com.selegant.datax.service.DataxDatasourceService;
import com.selegant.datax.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dataX")
public class DataXController {

    @Autowired
    DataxDatasourceService dataxDatasourceService;

    @RequestMapping("datasource/pageList")
    public PageInfoResponse datasourcePageList(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize,
                                               String name) {
        return dataxDatasourceService.datasourcePageList(pageNo, pageSize, name);
    }

    @PostMapping("testDataSource")
    public Result testDataSource(@RequestBody DataxDatasource dataxDatasource) {
        return dataxDatasourceService.testDataSource(dataxDatasource);
    }

    @PostMapping("updateDataSource")
    public Result updateDataSource(@RequestBody DataxDatasource dataxDatasource) {
        return dataxDatasourceService.updateDataSource(dataxDatasource);
    }

    @PostMapping("saveDataSource")
    public Result saveDataSource(@RequestBody DataxDatasource dataxDatasource) {
        return dataxDatasourceService.saveDataSource(dataxDatasource);
    }

    @DeleteMapping("deleteDataSource")
    public Result deleteDataSource(String id) {
        dataxDatasourceService.removeById(id);
        return ResultUtil.setSuccess("");
    }

    @GetMapping("datasource/list")
    public Result datasourceList() {
        return dataxDatasourceService.datasourceList();
    }

    @GetMapping("getTables")
    public Result getTables(String id) {
        return dataxDatasourceService.getTables(id);
    }

    @GetMapping("getColumns")
    public Result getColumns(String id,String tableName) {
        return dataxDatasourceService.getColumns(id,tableName);
    }

    @PostMapping("generateDataXJson")
    public Result generateDataXJson(@RequestBody GenerateDataXRequest request) {
        return dataxDatasourceService.generateDataXJson(request);
    }
}
