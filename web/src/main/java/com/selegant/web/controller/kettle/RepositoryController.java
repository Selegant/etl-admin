package com.selegant.web.controller.kettle;

import com.selegant.kettle.common.ResultResponse;
import com.selegant.kettle.model.KettleRepository;
import com.selegant.kettle.response.PageInfoResponse;
import com.selegant.kettle.service.KettleRepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("repository")
public class RepositoryController {

    final
    KettleRepositoryService kettleRepositoryService;

    public RepositoryController(KettleRepositoryService kettleRepositoryService) {
        this.kettleRepositoryService = kettleRepositoryService;
    }

    @RequestMapping("pageList")
    @ResponseBody
    public PageInfoResponse pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length) {
        return kettleRepositoryService.pageList(start,length);
    }


    @PostMapping("testConnection")
    @ResponseBody
    public ResultResponse testConnection(KettleRepository kettleRepository) {
        return kettleRepositoryService.testConnection(kettleRepository);
    }


    @PostMapping("save")
    @ResponseBody
    public ResultResponse save(KettleRepository kettleRepository) {
        return kettleRepositoryService.saveRepository(kettleRepository);
    }

    @PostMapping("update")
    @ResponseBody
    public ResultResponse update(KettleRepository kettleRepository) {
        return kettleRepositoryService.updateRepository(kettleRepository);
    }

    @PostMapping("check")
    @ResponseBody
    public ResultResponse check(Integer id) {
        return kettleRepositoryService.checkRepository(id);
    }

    @DeleteMapping("delete")
    @ResponseBody
    public ResultResponse delete(Integer id) {
        return kettleRepositoryService.deleteRepository(id);
    }

}
