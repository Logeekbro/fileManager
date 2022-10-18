package com.db.service;

import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.Watcher;
import com.db.Application;
import com.db.common.Vars;
import com.db.log.Logger;
import com.db.log.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.concurrent.CompletableFuture;

public class WatcherService {

    private static final Logger logger = LoggerFactory.getLogger();

    /**
     * 普通文件的监听器
     * @return 监听器
     */
    public static Watcher getWatcher(){
        return new SimpleWatcher() {

            /**
             * 文件创建时触发
             * @param event 事件
             * @param currentPath 监听目录
             */
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                // 获取文件名
                Object context = event.context();
                String fileName = context + "";
                Path filePath = Paths.get(currentPath + File.separator + fileName);
                if(Files.notExists(filePath) || Files.isDirectory(filePath)){
                    return;
                }
                // 不处理文件名中带新建的文件
                if(fileName.contains("新建")){
                    return;
                }
                logger.info("创建文件：" + fileName);
                // 处理文件移动
                CompletableFuture.runAsync(() ->
                        FileMoveService.move(currentPath, fileName));
            }

            /**
             * 文件被修改时触发
             * @param event 事件
             * @param currentPath 监听目录
             */
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                // 获取文件名
                Object context = event.context();
                String fileName = context + "";
                Path filePath = Paths.get(currentPath + File.separator + fileName);
                if(Files.notExists(filePath) || Files.isDirectory(filePath) || !Vars.movingFiles.containsKey(fileName)){
                    return;
                }
                logger.info("修改文件：" + context);
                CompletableFuture.runAsync(() -> Vars.movingFiles.replace(fileName, System.currentTimeMillis()));
            }
        };
    }

    /**
     * 配置文件的监听器，用于实时响应配置的变化
     * @return 监听器
     */
    public static Watcher getConfigWatcher(){
        return new SimpleWatcher() {
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                // 先检查是否有未完成的工作
                if(Vars.movingFiles.isEmpty()){
                    // 重新读取配置
                    ConfigService.loadConfig();
                    logger.info("配置文件发生变化，准备重新加载配置...");
                    // 重新启动服务
                    FileWatchService.closeAll();
                    Application.init();
                }
                else {
                    logger.error("有正在进行的工作，无法重新加载配置，请稍后重试->" + Vars.movingFiles);
                }
            }
        };
    }
}
