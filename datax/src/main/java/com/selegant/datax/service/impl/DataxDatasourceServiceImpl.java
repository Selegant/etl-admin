package com.selegant.datax.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.datax.base.Result;
import com.selegant.datax.mapper.DataxDatasourceMapper;
import com.selegant.datax.model.DataxDatasource;
import com.selegant.datax.request.GenerateDataXRequest;
import com.selegant.datax.response.PageInfoResponse;
import com.selegant.datax.service.DataxDatasourceService;
import com.selegant.datax.tool.QueryToolFactory;
import com.selegant.datax.tool.datax.DataXJson;
import com.selegant.datax.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@Service
@Slf4j
public class DataxDatasourceServiceImpl extends ServiceImpl<DataxDatasourceMapper, DataxDatasource> implements DataxDatasourceService{



    @Override
    public Result saveDataSource(DataxDatasource dataxDatasource) {
        // 测试数据库是否正常
        // 保存数据库信息
        dataxDatasource.setUpdateDate(new Date());
        save(dataxDatasource);
        return ResultUtil.setSuccess("");
    }

    @Override
    public PageInfoResponse datasourcePageList(int pageNo, int pageSize, String name) {
        QueryWrapper<DataxDatasource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        Page<DataxDatasource> page = new Page<>(pageNo, pageSize);
        IPage<DataxDatasource> list = page(page,queryWrapper);

        PageInfoResponse info = new PageInfoResponse();
        info.setData(list.getRecords());
        info.setTotalCount(list.getTotal());
        info.setPageNo(pageNo);
        info.setPageSize(pageSize);
        return info;
    }

    @Override
    public Result testDataSource(DataxDatasource dataxDatasource) {
        String driverName=dataxDatasource.getJdbcDriverClass();//这是要连接的数据库加载器
        String dbURL=dataxDatasource.getJdbcUrl();//这是要连接的端口号以及数据库名称
        String userName=dataxDatasource.getJdbcUsername();//用户名
        String userPassword=dataxDatasource.getJdbcPassword();//用户密码
        try {
            Class.forName(driverName);
            log.debug("加载驱动成功");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            log.error(e.getMessage(),e);
            log.debug("加载驱动失败");
            return ResultUtil.setError("","加载驱动失败");
        }
        try {
            Connection dbConn= DriverManager.getConnection(dbURL,userName,userPassword);
//            System.out.println("连接数据库成功");
            log.debug("连接数据库成功");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("数据库连接失败");
            log.error("数据库连接失败");
            return ResultUtil.setError("","数据库连接失败");
        }
        return ResultUtil.setSuccess("");
    }

    @Override
    public Result updateDataSource(DataxDatasource dataxDatasource) {
        updateById(dataxDatasource);
        return ResultUtil.setSuccess("");
    }

    @Override
    public Result datasourceList() {
        return ResultUtil.setSuccess(list());
    }

    @Override
    public Result getTables(String id) {
        DataxDatasource dataxDatasource = getById(id);
        if (ObjectUtil.isNotEmpty(dataxDatasource)){
            List<String> list = QueryToolFactory.getByDbType(dataxDatasource).getTables();
            return ResultUtil.setSuccess(list);
        }
        return ResultUtil.setError("无此数据源");
    }

    @Override
    public Result getColumns(String id, String tableName) {
        DataxDatasource dataxDatasource = getById(id);
        List<Map<String,String>> resultList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(dataxDatasource)){
            List<String> list = QueryToolFactory.getByDbType(dataxDatasource).getColumns(tableName);
            list.forEach(s->{
                Map<String,String> map = new HashMap<>(16);
                map.put("label",s);
                map.put("value",s);
                resultList.add(map);
            });
            return ResultUtil.setSuccess(list);
        }
        return ResultUtil.setError("无此数据源");
    }

    @Override
    public Result generateDataXJson(GenerateDataXRequest request) {
        DataxDatasource originDatasource = getById(request.getOriginDatasourceId());
        DataxDatasource targetDatasource = getById(request.getTargetDatasourceId());

        DataXJson dataXJson = new DataXJson();
        DataXJson.JobBean jobBean = new DataXJson.JobBean();
        DataXJson.JobBean.SettingBean settingBean = new DataXJson.JobBean.SettingBean();
        DataXJson.JobBean.SettingBean.ErrorLimitBean errorLimitBean = new DataXJson.JobBean.SettingBean.ErrorLimitBean();
        errorLimitBean.setPercentage(0.02);
        errorLimitBean.setRecord(0);
        DataXJson.JobBean.SettingBean.SpeedBean speedBean = new DataXJson.JobBean.SettingBean.SpeedBean();
        speedBean.setChannel(3);
        settingBean.setSpeed(speedBean);
        settingBean.setErrorLimit(errorLimitBean);

        DataXJson.JobBean.ContentBean contentBean = new DataXJson.JobBean.ContentBean();


        DataXJson.JobBean.ContentBean.ReaderBean readerBean = new DataXJson.JobBean.ContentBean.ReaderBean();
        readerBean.setName(originDatasource.getDatasource()+"reader");
        DataXJson.JobBean.ContentBean.ReaderBean.ReaderParameterBean parameterBean = new DataXJson.JobBean.ContentBean.ReaderBean.ReaderParameterBean();
        parameterBean.setUsername(originDatasource.getJdbcUsername());
        parameterBean.setPassword(originDatasource.getJdbcPassword());
        parameterBean.setSplitPk(request.getSplitPk());
        parameterBean.setColumn(request.getOriginColumns());
        List<DataXJson.JobBean.ContentBean.ReaderBean.ReaderParameterBean.ConnectionBean> originConnections = new ArrayList<>();
        DataXJson.JobBean.ContentBean.ReaderBean.ReaderParameterBean.ConnectionBean originConnection = new DataXJson.JobBean.ContentBean.ReaderBean.ReaderParameterBean.ConnectionBean();
        List<String> originJdbcUrls = new ArrayList<>();
        originJdbcUrls.add(originDatasource.getJdbcUrl());
        originConnection.setJdbcUrl(originJdbcUrls);
        List<String> originTables = new ArrayList<>();
        originTables.add(request.getOriginTableName());
        originConnection.setTable(originTables);
        originConnections.add(originConnection);
        parameterBean.setConnection(originConnections);
        readerBean.setParameter(parameterBean);
        contentBean.setReader(readerBean);


        DataXJson.JobBean.ContentBean.WriterBean writerBean = new DataXJson.JobBean.ContentBean.WriterBean();
        writerBean.setName(targetDatasource.getDatasource()+"writer");
        DataXJson.JobBean.ContentBean.WriterBean.WriterParameterBean writeParams = new DataXJson.JobBean.ContentBean.WriterBean.WriterParameterBean();
        writeParams.setUsername(targetDatasource.getJdbcUsername());
        writeParams.setPassword(targetDatasource.getJdbcPassword());
        writeParams.setWriteMode(request.getWriteMode());
        List<String> preSqls = new ArrayList<>();
        preSqls.add(request.getPreSql());
        writeParams.setPreSql(preSqls);
        writeParams.setColumn(request.getTargetColumns());
        List<DataXJson.JobBean.ContentBean.WriterBean.WriterParameterBean.ConnectionBeanX> targetConnections = new ArrayList<>();
        DataXJson.JobBean.ContentBean.WriterBean.WriterParameterBean.ConnectionBeanX targetConnection = new DataXJson.JobBean.ContentBean.WriterBean.WriterParameterBean.ConnectionBeanX();
        targetConnection.setJdbcUrl(targetDatasource.getJdbcUrl());
        List<String> targetTables = new ArrayList<>();
        targetTables.add(request.getTargetTableName());
        targetConnection.setTable(targetTables);
        targetConnections.add(targetConnection);
        writeParams.setConnection(targetConnections);
        writerBean.setParameter(writeParams);
        contentBean.setWriter(writerBean);

        List<DataXJson.JobBean.ContentBean> contentBeans = new ArrayList<>();
        contentBeans.add(contentBean);
        jobBean.setSetting(settingBean);
        jobBean.setContent(contentBeans);
        dataXJson.setJob(jobBean);

        return ResultUtil.setSuccess(JSONObject.toJSONString(dataXJson));
    }
}
