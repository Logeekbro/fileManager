package com.db.common;


import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class Vars {

//    public static final String rootDir = "D:\\StudyProjects\\JavaFX\\fileManager\\gui2";
    public static final String rootDir = System.getProperty("user.dir");

    public static Config config = new Config();

    public static final String configFilePath = rootDir + File.separator + "config.data";

    public static Map<String, Long> movingFiles = new ConcurrentSkipListMap<>();


}
