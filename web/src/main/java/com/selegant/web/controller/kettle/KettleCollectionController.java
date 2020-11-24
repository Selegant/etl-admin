package com.selegant.web.controller.kettle;

import com.selegant.kettle.common.ResultResponse;
import com.selegant.kettle.service.KettleCollectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author selegant
 */
@RestController
@RequestMapping("kettleCollection")
public class KettleCollectionController {

    final
    KettleCollectionService kettleCollectionService;

    public KettleCollectionController(KettleCollectionService kettleCollectionService) {
        this.kettleCollectionService = kettleCollectionService;
    }


    @GetMapping("/statistical")
    public ResultResponse statistical() {
        return kettleCollectionService.statisticalDataVolume();
    }


}
