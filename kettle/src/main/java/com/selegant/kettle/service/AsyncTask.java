package com.selegant.kettle.service;

import com.selegant.kettle.mapper.KettleRepositoryMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author selegant
 */
@Service
public class AsyncTask {

    final
    KettleRepositoryMapper kettleRepositoryMapper;

    public AsyncTask(KettleRepositoryMapper kettleRepositoryMapper) {
        this.kettleRepositoryMapper = kettleRepositoryMapper;
    }

    @Async
    void truncateRecord(){
        kettleRepositoryMapper.truncateRecord();
    }
}
