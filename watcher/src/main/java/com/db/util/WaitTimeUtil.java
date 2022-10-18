package com.db.util;

import cn.hutool.core.io.file.FileNameUtil;
import com.db.common.Config;
import com.db.common.Vars;

public class WaitTimeUtil {

    /**
     * 获取文件的延迟移动时间
     * @param fileName 文件名，用于获取文件类型
     * @return 文件的延迟移动时间
     */
    public static long getWaitTime(String fileName){
        String ext = FileNameUtil.extName(fileName);
        Long time = Vars.config.waitTimeMap.get(ext);
        return time == null ? Vars.config.waitTime : time;
    }
}
