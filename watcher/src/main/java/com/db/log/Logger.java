package com.db.log;


public class Logger {

    protected Logger(){}

    public void info(String info){
        LogHandler.add(info, LogType.INFO);
    }

    public void warn(String info){
        LogHandler.add(info, LogType.WARNING);
    }

    public void error(String errorMsg){
        LogHandler.add(errorMsg, LogType.ERROR);
    }

}
