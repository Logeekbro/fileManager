package com.db.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Config implements Serializable {

    public Set<String> watchFolders = new HashSet<>();

    public String targetFolder = "AAAABBBB";

    public Long waitTime = 10000L;

    public Map<String, Long> waitTimeMap = new HashMap<>();
}
