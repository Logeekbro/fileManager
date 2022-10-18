package com.db.service;

import com.db.common.Config;
import com.db.common.Vars;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigService {

    public void updateConfig() {
        try{
            try (FileOutputStream fis = new FileOutputStream(Vars.configFilePath)){
                ObjectOutputStream oos = new ObjectOutputStream(fis);
                oos.writeObject(Vars.config);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readConfig() {
        try{
            try (FileInputStream fis = new FileInputStream(Vars.configFilePath)){
                ObjectInputStream inputStream = new ObjectInputStream(fis);
                Vars.config = (Config) inputStream.readObject();
            }

        }
        catch (FileNotFoundException ex){
            updateConfig();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
