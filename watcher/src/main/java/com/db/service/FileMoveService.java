package com.db.service;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.db.common.Config;
import com.db.common.Vars;
import com.db.log.Logger;
import com.db.log.LoggerFactory;
import com.db.util.SleepUtil;
import com.db.util.WaitTimeUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


public class FileMoveService {

    private static final Logger logger = LoggerFactory.getLogger();

    public static void move(Path currentPath, String fileName){
        Vars.movingFiles.put(fileName, System.currentTimeMillis());
        long currentTime = 0;
        // 文件的延迟移动时间
        long waitT = WaitTimeUtil.getWaitTime(fileName);
        SleepUtil.sleep(waitT);
        Path source = Paths.get(currentPath + File.separator + fileName);
        // 第一次检查，避免文件创建后在延迟移动时间内被删除的情况
//        if(!Files.exists(source)){
//            Vars.movingFiles.remove(fileName);
//            logger.warn("1-文件已被删除：" + source);
//            return;
//        }
        // 计时器
        while ((Vars.movingFiles.get(fileName) - currentTime) > 0){
            long waitTime = waitT - (System.currentTimeMillis() - Vars.movingFiles.get(fileName));
            waitTime = waitTime < 0 ? 0 : waitTime;
            currentTime = Vars.movingFiles.get(fileName);
            SleepUtil.sleep(waitTime);
        }
        if(!Files.exists(source)){
            Vars.movingFiles.remove(fileName);
            logger.warn("文件已被删除：" + source);
            return;
        }
        Path move = moveFileToTarget(currentPath, source);
        logger.info("已移动：" + move);
        Vars.movingFiles.remove(fileName);
    }

    public static void init(String folderName){
        logger.info("初始化文件夹...");
        Path path = Paths.get(folderName);
        try {
            try(Stream<Path> fs =  Files.list(path)){
                fs.forEach(source -> {
                    if(!Files.isDirectory(source)){
                        CompletableFuture.runAsync(() -> {
                            Path move = moveFileToTarget(path, source);
                            logger.info("已移动：" + move);
                        });
                    }
                });
            }
        } catch (IOException e) {
            logger.error("初始化失败：" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param folderName 监听目录
     * @param file 要移动文件的路径
     * @return 文件移动后的路径
     */
    public static Path moveFileToTarget(Path folderName, Path file){
        String ext = FileNameUtil.extName(file.getFileName().toString());
        ext = ext.equals("") ? "【未知类型文件】" : ext;
        String targetPath =
                folderName + File.separator + Vars.config.targetFolder + File.separator + ext + File.separator + file.getFileName();
        Path target = Paths.get(targetPath);
        return FileUtil.move(file, target, true);
    }


}
