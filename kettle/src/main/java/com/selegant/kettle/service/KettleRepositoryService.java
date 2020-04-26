package com.selegant.kettle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selegant.kettle.common.ResultResponse;
import com.selegant.kettle.common.ResultUtils;
import com.selegant.kettle.init.KettleInit;
import com.selegant.kettle.mapper.KettleRepositoryMapper;
import com.selegant.kettle.model.KettleRepository;
import com.selegant.kettle.response.PageInfoResponse;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class KettleRepositoryService {

    @Autowired
    KettleInit kettleInit;

    private Logger logger = LoggerFactory.getLogger(KettleRepositoryService.class);

    @Autowired
    KettleRepositoryMapper kettleRepositoryMapper;


    public PageInfoResponse pageList(int start, int length) {
        KettleRepository kettleRepository = new KettleRepository();
        kettleRepository.setDelFlag(0);
        QueryWrapper<KettleRepository> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        PageHelper.startPage(start, length);
        List<KettleRepository> list = kettleRepositoryMapper.selectList(queryWrapper);
        list.forEach(s -> {
            s.setDatabasePassword("");
            s.setRepositoryPassword("");
        });
        PageInfo<KettleRepository> pageInfo = PageInfo.of(list);
        PageInfoResponse info = new PageInfoResponse();
        info.setData(list);
        info.setTotalCount(pageInfo.getSize());
        info.setTotalPage(pageInfo.getSize());
        info.setPageNo(start);
        info.setPageSize(length);
        return info;
    }

    public ResultResponse testConnection(KettleRepository kettleRepository) {
        try {
//            KettleDatabaseRepository repository = new KettleDatabaseRepository();
//            KettleDatabaseRepositoryMeta repositoryMeta = new KettleDatabaseRepositoryMeta();
//            DatabaseMeta databaseMeta = new DatabaseMeta(kettleRepository.getRepositoryName(), "MYSQL", "Native", kettleRepository.getDatabaseHost(), kettleRepository.getDatabaseName(), "3306", kettleRepository.getDatabaseUsername(), kettleRepository.getDatabasePassword());
//            repositoryMeta.setConnection(databaseMeta);
//            repository.init(repositoryMeta);
//            repository.connect(kettleRepository.getRepositoryUsername(), kettleRepository.getRepositoryPassword());
            KettleDatabaseRepository kettleDatabaseRepository = kettleInit.loadKettleDatabaseRepository();
            RepositoryDirectoryInterface rd = kettleDatabaseRepository.loadRepositoryDirectoryTree();
            if (Objects.isNull(rd)) {
                return ResultUtils.setError("测试失败");
            }
        } catch (KettleException e) {
            logger.error(e.getMessage(), e);
            return ResultUtils.setError(e.getMessage());
        }
        return ResultUtils.setOk();
    }

    public ResultResponse saveRepository(KettleRepository kettleRepository) {
        kettleRepository.setAddTime(new Date());
        kettleRepository.setEditTime(new Date());
        if(kettleRepositoryMapper.insert(kettleRepository) > 0){
            return ResultUtils.setOk();
        }else {
            return ResultUtils.setError("新增失败");
        }
    }

    public ResultResponse updateRepository(KettleRepository kettleRepository) {
        if(kettleRepositoryMapper.updateById(kettleRepository) > 0){
            return ResultUtils.setOk();
        }else {
            return ResultUtils.setError("更新失败");
        }
    }

    public ResultResponse deleteRepository(Integer  repositoryId) {
        KettleRepository kettleRepository = kettleRepositoryMapper.selectById(repositoryId);
        if(1==kettleRepository.getUseFlag()){
            return ResultUtils.setError("正在使用的资源库无法删除");
        }
        if(kettleRepositoryMapper.deleteById(repositoryId) > 0){
            return ResultUtils.setOk();
        }else {
            return ResultUtils.setError("删除失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultResponse checkRepository(Integer id) {
        KettleRepository kettleRepository = new KettleRepository();
        kettleRepository.setRepositoryId(id);
        kettleRepository.setUseFlag(1);
        if(kettleRepositoryMapper.updateById(kettleRepository) > 0){
            kettleRepositoryMapper.updateOtherUseFlag(id);
            kettleRepositoryMapper.truncateRecord();
            return ResultUtils.setOk();
        }else {
            return ResultUtils.setError("切换成功");
        }
    }
}
