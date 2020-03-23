package com.selegant.kettle.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import org.pentaho.di.core.logging.LogLevel;

public class BaseJobHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(String s) {
        return null;
    }

    LogLevel getLogLevel(int logLevel){
        switch (logLevel){
            case 0:return LogLevel.NOTHING;
            case 1:return LogLevel.ERROR;
            case 2:return LogLevel.MINIMAL;
            case 4:return LogLevel.DETAILED;
            case 5:return LogLevel.DEBUG;
            case 6:return LogLevel.ROWLEVEL;
            default:return LogLevel.BASIC;
        }
    }
}
