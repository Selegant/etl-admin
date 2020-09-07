package com.selegant.web.controller.kettle;

import cn.hutool.core.util.StrUtil;
import com.selegant.kettle.common.ResultResponse;
import com.selegant.kettle.common.ResultUtils;
import com.selegant.kettle.init.KettleInit;
import com.selegant.kettle.mapper.XxlJobInfoMapper;
import com.selegant.kettle.model.KettleResource;
import com.selegant.kettle.request.SyncKettleResource;
import com.selegant.kettle.response.PageInfoResponse;
import com.selegant.kettle.service.KettleResourceService;
import org.pentaho.di.core.exception.KettleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kettle")
public class KettleController {

    private Logger logger = LoggerFactory.getLogger(KettleController.class);

    @Autowired
    KettleInit kettleInit;

    @Autowired
    KettleResourceService kettleResourceService;

    @Autowired
    XxlJobInfoMapper xxlJobInfoMapper;

    @RequestMapping("job/pageList")
    @ResponseBody
    public PageInfoResponse jobPageList(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                     @RequestParam(required = false, defaultValue = "10") int pageSize,
                                        String name) {
        return kettleResourceService.pageJobList(pageNo,pageSize,2,name);
    }

    @RequestMapping("trans/pageList")
    @ResponseBody
    public PageInfoResponse transPageList(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                     @RequestParam(required = false, defaultValue = "10") int pageSize,
                                          String name) {
        return kettleResourceService.pageJobList(pageNo,pageSize,1,name);
    }

    @RequestMapping("getKettleResourceList/{objectType}")
    @ResponseBody
    public ResultResponse getKettleResourceList(@PathVariable(value = "objectType") int objectType) throws KettleException {
        return kettleResourceService.getKettleResourceList(objectType);
    }

    @PostMapping("syncJobAndTrans")
    @ResponseBody
    public ResultResponse syncJobAndTrans(@RequestBody SyncKettleResource resource) throws KettleException {
        return kettleResourceService.syncJobAndTrans(resource);
    }


    @PostMapping("job/syncJob")
    @ResponseBody
    public ResultResponse syncJob() throws KettleException {
        return kettleResourceService.syncJob(2);
    }

    @PostMapping("trans/syncTrans")
    @ResponseBody
    public ResultResponse syncTrans() throws KettleException {
        return kettleResourceService.syncJob(1);
    }

    @PostMapping("job/generateParams")
    @ResponseBody
    public ResultResponse generateParams(@RequestBody KettleResource kettleResource) {
        return kettleResourceService.generateParams(kettleResource);
    }

    @PostMapping("job/truncateJob")
    @ResponseBody
    public ResultResponse truncateJob() {
        return kettleResourceService.truncateJobAndTrans(2);
    }

    @PostMapping("trans/truncateTrans")
    @ResponseBody
    public ResultResponse truncateTrans() {
        return kettleResourceService.truncateJobAndTrans(1);
    }


    @DeleteMapping("deleteResource/{id}")
    @ResponseBody
    public ResultResponse deleteJob(@PathVariable(value = "id") Integer id) {
        return kettleResourceService.deleteJobAndTrans(id);
    }

    @DeleteMapping("deleteBatchResource")
    @ResponseBody
    public ResultResponse deleteBatchResource(String ids) {
        if(StrUtil.isBlank(ids)){
            return ResultUtils.setError("Ids不能为空");
        }
        String[] strings = null;
        if(ids.contains(",")){
            strings = ids.split(",");
        }else {
            return kettleResourceService.deleteJobAndTrans(Integer.parseInt(ids));
        }
        for (String id : strings) {
            kettleResourceService.deleteJobAndTrans(Integer.parseInt(id));
        }
        return ResultUtils.setOk();
    }


    @PostMapping("stop")
    @ResponseBody
    public ResultResponse stop(Integer jobId) {
//        XxlJobInfo xxlJobInfo = new XxlJobInfo();
//        xxlJobInfo.setId(jobId);
//        xxlJobInfo = xxlJobInfoMapper.selectOne(xxlJobInfo);
//        if(xxlJobInfo.getObjectType()==2){
//            try {
//                KettleParams kettleParams = JSONObject.parseObject(xxlJobInfo.getExecutorParam(),KettleParams.class);
//                RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory(kettleParams.getObjectDirectory());
//                JobMeta jobMeta = kettleDatabaseRepository.loadJob(kettleParams.getObjectName(),directory,new ProgressNullMonitorListener(),null );
//                Job job = new Job(kettleDatabaseRepository,jobMeta);
//                job.stopAll();
//                LoggingBuffer appender = KettleLogStore.getAppender();
//                String logChannelId = job.getLogChannelId();
//                logger.info("日志内容:"+appender.getBuffer(logChannelId, true).toString());
//                if(job.getErrors()>0){
//                    return ResultUtils.setError("停止失败");
//                }
//            } catch (KettleException e) {
//                e.printStackTrace();
//                logger.error(e.getMessage(),e);
//                return ResultUtils.setError("停止失败");
//            }
//        }else {
//            try {
//                KettleParams kettleParams = JSONObject.parseObject(xxlJobInfo.getExecutorParam(),KettleParams.class);
//                RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory(kettleParams.getObjectDirectory());
//                TransMeta transMeta = kettleDatabaseRepository.loadTransformation(kettleParams.getObjectName(),directory,new ProgressNullMonitorListener(),true,null );
//                Trans trans = new Trans(transMeta);
//                trans.stopAll();
//                LoggingBuffer appender = KettleLogStore.getAppender();
//                String logChannelId = trans.getLogChannelId();
//                logger.info("日志内容:"+appender.getBuffer(logChannelId, true).toString());
//                if(trans.getErrors()>0){
//                    return ResultUtils.setError("停止失败");
//                }
//            } catch (KettleException e) {
//                e.printStackTrace();
//                logger.error(e.getMessage(),e);
//                return ResultUtils.setError("停止失败");
//            }
//        }
        return ResultUtils.setError("停止成功");
    }

}
