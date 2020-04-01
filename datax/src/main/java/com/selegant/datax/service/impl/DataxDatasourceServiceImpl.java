package com.selegant.datax.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.selegant.datax.base.Result;
import com.selegant.datax.response.PageInfoResponse;
import com.selegant.datax.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.selegant.datax.model.DataxDatasource;
import com.selegant.datax.mapper.DataxDatasourceMapper;
import com.selegant.datax.service.DataxDatasourceService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

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
}
