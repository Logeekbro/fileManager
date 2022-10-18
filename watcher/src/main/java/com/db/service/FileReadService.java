package com.db.service;

import com.db.common.Config;
import com.db.common.Vars;
import com.db.log.Logger;
import com.db.log.LoggerFactory;
import com.db.util.SleepUtil;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class FileReadService {


    private static final Logger logger = LoggerFactory.getLogger();

    public static Config ReadConfig(){
        try{
            try (FileInputStream fis = new FileInputStream(Vars.configFilePath)){
                ObjectInputStream inputStream = new ObjectInputStream(fis);
                return (Config) inputStream.readObject();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("配置文件读取失败-> " + e.getMessage());
            System.out.println("程序将在4s后退出...");
            SleepUtil.sleep(4000);
            System.exit(0);
        }
        return null;
    }

}
