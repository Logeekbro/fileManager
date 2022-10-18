package com.db.log;


/**
 * 没啥用的工厂
 */
public class LoggerFactory {

    private static final Logger logger = new Logger();

    public static Logger getLogger(){
        return logger;
    }

}
