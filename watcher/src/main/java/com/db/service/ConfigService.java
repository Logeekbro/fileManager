package com.db.service;

import com.db.common.Vars;


public class ConfigService {

        public static void loadConfig(){
                Vars.config = FileReadService.ReadConfig();
        }

}
