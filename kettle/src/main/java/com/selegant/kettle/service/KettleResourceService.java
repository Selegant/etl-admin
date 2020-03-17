package com.selegant.kettle.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selegant.kettle.common.ResultResponse;
import com.selegant.kettle.common.ResultUtils;
import com.selegant.kettle.mapper.*;
import com.selegant.kettle.model.KettleParams;
import com.selegant.kettle.model.KettleResource;
import com.selegant.kettle.model.XxlJobGroup;
import com.selegant.kettle.model.XxlJobInfo;
import com.selegant.kettle.response.PageInfoResponse;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class KettleResourceService extends ServiceImpl<KettleResourceMapper,KettleResource> {

    private Logger logger = LoggerFactory.getLogger(KettleResourceService.class);

    @Autowired
    KettleDatabaseRepository kettleDatabaseRepository;

    @Autowired
    KettleResourceMapper kettleResourceMapper;

    @Autowired
    XxlJobInfoMapper xxlJobInfoMapper;

    @Autowired
    XxlJobGroupMapper xxlJobGroupMapper;

    @Autowired
    XxlJobLogglueMapper xxlJobLogglueMapper;

    @Autowired
    XxlJobLogMapper xxlJobLogMapper;


    public PageInfoResponse pageJobList(int pageNo, int pageSize,int objectType,String name) {
        KettleResource kettleResource = new KettleResource();
        kettleResource.setObjectType(objectType);
        QueryWrapper<KettleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type",objectType);
        if(StrUtil.isNotBlank(name)){
            queryWrapper.eq("name",name);
        }
        Page<KettleResource> page = new Page<>(pageNo, pageSize);
        IPage<KettleResource> list = page(page,queryWrapper);

        PageInfoResponse info = new PageInfoResponse();
        info.setData(list.getRecords());
        info.setTotalCount(list.getTotal());
//        info.setTotalPage(list.getPages());
        info.setPageNo(pageNo);
        info.setPageSize(pageSize);
        return info;
    }


    /**
     * 同步Kettle资源库作业信息到本地数据库
     * @return
     * @throws KettleException
     */
    public ResultResponse syncJob(int objectType) throws KettleException {
        //加载kettle资源文件夹树形列表
        RepositoryDirectoryInterface rDirectory = kettleDatabaseRepository.loadRepositoryDirectoryTree();

        //获取树形列表的所有叶子节点信息
        List<RepositoryDirectoryInterface> list = rDirectory.getChildren();

        //遍历树形文件列表将叶子节点存入一个列表中
        List<RepositoryDirectoryInterface> treeList = getList(list);

        //遍历所有叶子节点获取job和trans的所有具体信息
        List<RepositoryElementMetaInterface> jobAndTranList = new ArrayList<>();

        treeList.forEach(s -> {
            try {
                List<RepositoryElementMetaInterface> repositoryElementMetaInterfaceList =
                        kettleDatabaseRepository.getJobAndTransformationObjects(s.getObjectId(), false);
                if (repositoryElementMetaInterfaceList.size() > 0) {
                    jobAndTranList.addAll(repositoryElementMetaInterfaceList);
                }
            } catch (KettleException e) {
                logger.error(e.getMessage(), e);
            }
        });
        List<RepositoryElementMetaInterface> jobList;
        //过滤trans信息 获取所有job信息
        if(objectType==1){
            jobList = jobAndTranList.stream().filter(s -> "transformation".equals(s.getObjectType().getTypeDescription())).collect(Collectors.toList());
        }else {
            jobList = jobAndTranList.stream().filter(s -> "job".equals(s.getObjectType().getTypeDescription())).collect(Collectors.toList());
        }
        //同步所有job到本地库中
        syncResource(jobList, objectType);

        return ResultUtils.setOk();
    }

    /**
     * 同步kettle的job信息到本地库中
     * @param list
     * @param objectType
     * @return
     */
    @Transactional
    public List<KettleResource> syncResource(List<RepositoryElementMetaInterface> list, int objectType) {
        //解析Kettle作业列表
        List<KettleResource> kettleResources = new ArrayList<>();
        list.forEach(s -> {
            KettleResource kettleResource = new KettleResource();
            kettleResource.setName(s.getName());
            kettleResource.setRepositoryDirectory(s.getRepositoryDirectory().getPath());
            kettleResource.setModifiedUser(s.getModifiedUser());
            kettleResource.setModifiedDate(s.getModifiedDate());
            kettleResource.setObjectType(objectType);
            kettleResource.setObjectId(s.getObjectId().getId());
            kettleResource.setDescription(s.getDescription());
            kettleResource.setDeleted(s.isDeleted());
            kettleResource.setUpdateTime(new Date());
            kettleResources.add(kettleResource);
        });
        //查询已经存在的作业
        KettleResource kettleResource = new KettleResource();
        kettleResource.setObjectType(objectType);
        QueryWrapper<KettleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type",objectType);
        List<KettleResource> existList = kettleResourceMapper.selectList(queryWrapper);
        //将Kettle作业分成需要新增的以及修改的两个列表
        List<KettleResource> updateList = new ArrayList<>();
        List<KettleResource> insertList = new ArrayList<>();
        List<KettleResource> deleteList = new ArrayList<>();
        if (existList.size() == 0) {
            insertList.addAll(kettleResources);
        } else {
            //判断已经删除的资源
            List<String> resourceObjectIds = new ArrayList<>();
            kettleResources.forEach(s->resourceObjectIds.add(s.getObjectId()));
            existList.forEach(n->{
                if(!resourceObjectIds.contains(n.getObjectId())&&n.getObjectType()==objectType){
                    n.setDeleted(true);
                    deleteList.add(n);
                    updateList.add(n);
                }
            });
            //判断正常的资源
            kettleResources.forEach(n -> {
                //过滤所有已经存在的job信息，存在即更新不存在新增
                if(existList.stream().anyMatch(s -> s.getObjectId().equals(n.getObjectId()))){
                    updateList.add(n);
                }else {
                    insertList.add(n);
                };
            });
        }
        //判断 更新列表是否有被删除或者改名的任务
        if(!updateList.isEmpty()){
            updateList.forEach(s -> {
                KettleParams kettleParams = new KettleParams();
                kettleParams.setObjectId(s.getObjectId());
                kettleParams.setObjectName(s.getName());
                kettleParams.setObjectDirectory(s.getRepositoryDirectory());
                kettleParams.setLogLevel(3);
                s.setKettleParams(JSONObject.toJSONString(kettleParams));
                kettleResourceMapper.updateByObjectIdAndOrderType(s);
            });
            deleteExistResourceToXxlJob(deleteList);
            syncExistResourceToXxlJob(updateList);
        }
        //同步新增的任务
        if(!insertList.isEmpty()){
            insertList.stream().filter(s->!s.getDeleted()).forEach(s->{
                //同步新增任务生成默认调度参数
                //默认设置日志等级为基本日志
                s.setLogLevel(3);
                KettleParams kettleParams = new KettleParams();
                kettleParams.setObjectId(s.getObjectId());
                kettleParams.setObjectName(s.getName());
                kettleParams.setObjectDirectory(s.getRepositoryDirectory());
                kettleParams.setLogLevel(s.getLogLevel());
                //默认生成调用参数
                s.setKettleParams(JSONObject.toJSONString(kettleParams));
            });
//            kettleResourceMapper.insertList(insertList);
            saveBatch(insertList);
            //同步资源生成xxl_job任务
            syncNewResourceToXxlJob(insertList);
        }
        return kettleResources;
    }

    /**
     * 遍历树形文件夹获取所有叶子节点的信息
     * @param list
     * @return
     */
    private List<RepositoryDirectoryInterface> getList(List<RepositoryDirectoryInterface> list) {
        List<RepositoryDirectoryInterface> treeList = new ArrayList<>();
        list.forEach(s -> {
            treeList.add(s);
            if (s.getChildren().size() > 0) {
                treeList.addAll(getList(s.getChildren()));
            }
        });
        return treeList;
    }

    /**
     * 生成kettle调度参数
     * @param kettleResource
     * @return
     */
    public ResultResponse generateParams(KettleResource kettleResource) {
        KettleParams kettleParams = new KettleParams();
        kettleParams.setObjectId(kettleResource.getObjectId());
        kettleParams.setObjectName(kettleResource.getName());
        kettleParams.setObjectDirectory(kettleResource.getRepositoryDirectory());
        kettleParams.setLogLevel(kettleResource.getLogLevel());
        kettleResource.setUpdateTime(new Date());
        kettleResource.setKettleParams(JSONObject.toJSONString(kettleParams));
        if(kettleResourceMapper.updateById(kettleResource)<1){
            return ResultUtils.setError("生成调度参数失败");
        }
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setObjectId(kettleResource.getObjectId());
        xxlJobInfo.setObjectType(kettleResource.getObjectType());
        QueryWrapper<XxlJobInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_id",kettleResource.getObjectId());
        queryWrapper.eq("object_type",kettleResource.getObjectType());
        xxlJobInfo = xxlJobInfoMapper.selectOne(queryWrapper);
        xxlJobInfo.setExecutorParam(kettleResource.getKettleParams());
        if(xxlJobInfoMapper.updateById(xxlJobInfo)<1){
            return ResultUtils.setError("同步调度参数失败");
        }
        return ResultUtils.setOk(kettleResource);
    }

    /**
     * 清除任务
     * @return
     */
    public ResultResponse truncateJobAndTrans(Integer objectType) {
        KettleResource kettleResource = new KettleResource();
        kettleResource.setObjectType(objectType);
        QueryWrapper<KettleResource> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("object_type",objectType);
        if (kettleResourceMapper.delete(deleteWrapper) < 1) {
            return ResultUtils.setError("清除JOB失败");
        }
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setExecutorHandler(getJobHandler(objectType));
        QueryWrapper<XxlJobInfo> xxlJobInfoQueryWrapper = new QueryWrapper<>();
        xxlJobInfoQueryWrapper.eq("executor_handler",getJobHandler(objectType));
        List<XxlJobInfo> list = xxlJobInfoMapper.selectList(xxlJobInfoQueryWrapper);
        list.forEach(s -> {
            xxlJobLogglueMapper.deleteByJobId(s.getId());
            xxlJobLogMapper.deleteByJobId(s.getId());
        });
        if (xxlJobInfoMapper.delete(xxlJobInfoQueryWrapper) < 1) {
            return ResultUtils.setError("清除XXL_JOB失败");
        }
        return ResultUtils.setOk();
    }

    /**
     * 同步被修改过的Kettle作业到XXL_JOB
     * @param list
     * @return
     */
    private boolean syncExistResourceToXxlJob(List<KettleResource> list){
        QueryWrapper<XxlJobInfo> xxlJobInfoQueryWrapper = new QueryWrapper<>();
        List<XxlJobInfo> existJobList = xxlJobInfoMapper.selectList(xxlJobInfoQueryWrapper).stream().filter(s-> ObjectUtil.isNotEmpty(s.getObjectId())).collect(Collectors.toList());
        list.stream().filter(s-> !s.getDeleted()).collect(Collectors.toList()).forEach(s->{
            existJobList.forEach(e->{
                if(e.getObjectId().equals(s.getObjectId())&& e.getObjectType().equals(s.getObjectType())){
                    e.setExecutorParam(s.getKettleParams());
                    e.setJobDesc(s.getName());
                    e.setUpdateTime(new Date());
                    xxlJobInfoMapper.updateById(e);
                }
            });
        });
        return true;
    }


    /**
     * 将删除Kettle已经删除的作业在XXL_JOB里面也删除
     * @param list
     * @return
     */
    private boolean deleteExistResourceToXxlJob(List<KettleResource> list){
        list.forEach(s->{
            XxlJobInfo info = new XxlJobInfo();
            info.setObjectId(s.getObjectId());
            info.setObjectType(s.getObjectType());
            QueryWrapper<XxlJobInfo> xxlJobInfoQueryWrapper = new QueryWrapper<>();
            xxlJobInfoQueryWrapper.eq("object_id",s.getObjectId());
            xxlJobInfoQueryWrapper.eq("object_type",s.getObjectType());
            info = xxlJobInfoMapper.selectOne(xxlJobInfoQueryWrapper);
            xxlJobLogMapper.deleteByJobId(info.getId());
            xxlJobLogglueMapper.deleteByJobId(info.getId());
            xxlJobInfoMapper.deleteById(info.getId());
        });
        return true;
    }



    /**
     * 同步新的KETTLE作业到XXL_JOB
     * @param list
     * @return
     */
    private boolean syncNewResourceToXxlJob(List<KettleResource> list){
        XxlJobGroup xxlJobGroup = new XxlJobGroup();
        xxlJobGroup.setAppName("xxl-job-kettle");
        QueryWrapper<XxlJobGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_name","xxl-job-kettle");
        XxlJobGroup finalXxlJobGroup  = xxlJobGroupMapper.selectOne(queryWrapper);
//        List<XxlJobInfo> jobInfoList = new ArrayList<>();
        list.forEach(s->{
            XxlJobInfo info = new XxlJobInfo();
            info.setObjectId(s.getObjectId());
            info.setObjectType(s.getObjectType());
            //执行器ID
            info.setJobGroup(finalXxlJobGroup.getId());
            info.setJobDesc(s.getName());
            //默认Cron为1分钟1次
            info.setJobCron("0 0/1 * * * ? ");
            info.setAddTime(new Date());
            info.setUpdateTime(new Date());
            info.setAuthor(finalXxlJobGroup.getTitle());
            //执行器路由策略
            info.setExecutorRouteStrategy("FIRST");
            //根据任务类型选择JobHandler
            info.setExecutorHandler(getJobHandler(s.getObjectType()));
            //存入加载参数
            info.setExecutorParam(s.getKettleParams());
            //阻塞处理策略 默认设置SERIAL_EXECUTION
            info.setExecutorBlockStrategy("SERIAL_EXECUTION");
            info.setExecutorTimeout(0);
            info.setExecutorFailRetryCount(0);
            //GLUE类型 默认 BEAN
            info.setGlueType("BEAN");
            info.setGlueSource("");
            info.setGlueRemark("GLUE代码初始化");
            info.setGlueUpdatetime(new Date());
            info.setChildJobid("");
            info.setTriggerStatus((byte)0);
            info.setTriggerLastTime(0L);
            info.setTriggerNextTime(0L);
//            jobInfoList.add(info);
            xxlJobInfoMapper.insert(info);
        });
        return true;
    }

    /**
     * 根据任务类型选择JobHandler
     * @param objectType
     * @return
     */
    private String getJobHandler(int objectType){
        if(1==objectType){
            return "kettleTransHandler";
        }else {
            return "kettleJobHandler";
        }
    }

    /**
     * 删除单个资源同步到XXL_JOB
     * @param id
     * @return
     */
    @Transactional
    public ResultResponse deleteJobAndTrans(Integer id) {
        KettleResource kettleResource = kettleResourceMapper.selectById(id);
        if(Objects.isNull(kettleResource)){
            return ResultUtils.setError("查询资源失败");
        }
        if(kettleResourceMapper.deleteById(kettleResource.getId())>0){
            XxlJobInfo xxlJobInfo = new XxlJobInfo();
            xxlJobInfo.setObjectId(kettleResource.getObjectId());
            xxlJobInfo.setObjectType(kettleResource.getObjectType());
            QueryWrapper<XxlJobInfo> xxlJobInfoQueryWrapper = new QueryWrapper<>();
            xxlJobInfoQueryWrapper.eq("object_id",kettleResource.getObjectId());
            xxlJobInfoQueryWrapper.eq("object_type",kettleResource.getObjectType());
            xxlJobInfo = xxlJobInfoMapper.selectOne(xxlJobInfoQueryWrapper);
            xxlJobLogMapper.deleteByJobId(xxlJobInfo.getId());
            xxlJobLogglueMapper.deleteByJobId(xxlJobInfo.getId());
            if(xxlJobInfoMapper.deleteById(xxlJobInfo.getId())<0){
                return ResultUtils.setError("删除资源失败");
            }
            return ResultUtils.setOk();
        }else {
            return ResultUtils.setError("删除资源失败");
        }
    }
}
