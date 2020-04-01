package com.selegant.datax.service;

import com.selegant.datax.base.Result;
import com.selegant.datax.model.DataxDatasource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.selegant.datax.response.PageInfoResponse;

public interface DataxDatasourceService extends IService<DataxDatasource>{

    /**
     * 保存数据源
     * @param dataxDatasource
     * @return
     */
    Result saveDataSource(DataxDatasource dataxDatasource);

    /**
     * 查询数据源列表
     * @param pageNo 页码
     * @param pageSize 每页数
     * @param name 数据源名称
     * @return
     */
    PageInfoResponse datasourcePageList(int pageNo, int pageSize, String name);

    /**
     * 测试数据源连接
     * @param dataxDatasource
     * @return
     */
    Result testDataSource(DataxDatasource dataxDatasource);

    /**
     * 更新数据源
     * @param dataxDatasource
     * @return
     */
    Result updateDataSource(DataxDatasource dataxDatasource);
}
