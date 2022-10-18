package com.db.log;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import com.db.common.Vars;
import com.db.util.TimeUtil;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.*;

public class LogHandler {

    // 当日志任务超过此值时将会批量打印
    private static final long limit = 100;

    // 控制是否批量打印
    private static boolean batchMode = false;

    private static final Queue<String> tasks = new LinkedBlockingQueue<>();

    private static final Queue<String> times = new LinkedBlockingQueue<>();

    private static final Queue<LogType> types = new LinkedBlockingQueue<>();

    private static final String logPath =
            Vars.rootDir + File.separator + "logs" + File.separator + TimeUtil.getDate() + ".txt";

    private static final File file = FileUtil.file(logPath);

    private static final StringBuilder logInfo = new StringBuilder();

    private static final Locker locker1 = new Locker();

    private static final Locker locker2 = new Locker();



    protected static void add(String task, LogType type){
        times.add(TimeUtil.getTime());
        tasks.add(task);
        types.add(type);
        CompletableFuture.runAsync(LogHandler::saveLog);
    }


    private static void saveLog(){
        // 加锁，保证日志顺序
        locker1.lock();
        // 拼接日志信息
        String info = tasks.remove();
        String msg = "[" + types.remove() + "][" + times.remove() + "]: " + info;
        // 判断是否要批量打印日志
        if(tasks.size() <= limit) {
            batchMode = false;
            System.out.println(msg);
        }
        else {
            batchMode = true;
        }
        // 存入到总日志信息中
        logInfo.append(msg).append("\n");
        // 解锁
        locker1.unLock();
        // 如果没有新的日志任务
        locker2.lock();
        if(tasks.isEmpty()){
            if(batchMode)System.out.print(logInfo);
            // 将日志写入到文件中
            FileAppender appender = new FileAppender(file, logInfo.length(), false);
            appender.append(logInfo.toString());
            appender.flush();
            // 清空日志信息
            logInfo.delete(0 , logInfo.length()-1);
        }
        locker2.unLock();
    }


}
