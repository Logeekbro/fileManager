package com.db.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import com.db.common.Vars;
import com.db.log.Logger;
import com.db.log.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileWatchService {

    private static final Logger logger = LoggerFactory.getLogger();

    private static final List<WatchMonitor> watchers = new ArrayList<>();

    public static void startWatcher(String folderName){
        File file = FileUtil.file(folderName);
        if(file.isDirectory()){
            WatchMonitor watcher = WatchMonitor.createAll(file, new DelayWatcher(WatcherService.getWatcher(), 500));
            watcher.setMaxDepth(0);
            watcher.start();
            watchers.add(watcher);
            logger.info("正在监听：" + folderName);
        }
    }

    public static void startConfigWatcher(){
        File file = FileUtil.file(Vars.configFilePath);
        WatchMonitor watcher = WatchMonitor.createAll(file, new DelayWatcher(WatcherService.getConfigWatcher(), 500));
        watcher.setMaxDepth(0);
        watcher.start();
        watchers.add(watcher);
    }

    public static  void closeAll(){
        for(WatchMonitor watchMonitor : watchers){
            watchMonitor.close();
        }
        logger.info("已关闭所有监听器");
    }
}
