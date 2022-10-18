package com.db.common;


import java.io.File;

public class Vars {

    public static final String rootDir = System.getProperty("user.dir");

    public static Config config = new Config();

    public static String configFilePath = rootDir + File.separator + "config.data";

}
