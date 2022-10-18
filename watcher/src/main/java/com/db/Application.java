package com.db;

import com.db.common.Vars;
import com.db.log.Logger;
import com.db.log.LoggerFactory;
import com.db.service.ConfigService;
import com.db.service.FileMoveService;
import com.db.service.FileWatchService;

import java.util.Set;

/**
 * Hello world!
 *
 */
public class Application
{
    private static final Logger logger = LoggerFactory.getLogger();

    public static void main( String[] args )
    {
        init();
    }

    public static void init(){
        ConfigService.loadConfig();
        FileWatchService.startConfigWatcher();
        logger.info("应用所在目录: " + Vars.rootDir);
        Set<String> folders = Vars.config.watchFolders;
        for(String folder : folders){
//            FileMoveService.init(folder);
            FileWatchService.startWatcher(folder);
        }
    }
}
