package com.db.controller;

import com.db.common.Config;
import com.db.common.Vars;
import com.db.service.ConfigService;
import com.db.util.StringUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;


@FXMLController
public class SettingController implements Initializable {

    @Resource
    private ConfigService configService;

    @FXML
    private Label saveFolderTip;

    @FXML
    private TextField targetFolder;

    @FXML
    private TextField waitTime;

    @FXML
    private Label saveWaitTimeTip;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void init(){
        targetFolder.setText(Vars.config.targetFolder);
        waitTime.setText(String.valueOf(Vars.config.waitTime));
    }

    @FXML
    private void saveTargetFolder(){
        String folderName = targetFolder.getText();
        if(folderName.contains("/") || folderName.contains("\\")){
            saveFolderTip.setText("名称中不能包含 \\  或  /");
        }
        else{
            Vars.config.targetFolder = folderName;
            configService.updateConfig();
            saveFolderTip.setText("已保存");
        }
        saveFolderTip.setVisible(true);
    }


    public void hiddenTip() {
        saveFolderTip.setText("");
        saveWaitTimeTip.setText("");
    }

    public void saveWaitTime() {
        String time = waitTime.getText();
        // 如果填的值为空，则使用默认值
        if(!StringUtils.hasText(time)){
            Vars.config.waitTime = new Config().waitTime;
            saveWaitTimeTip.setText("已保存");
        }
        else if(StringUtil.isNumeric(time)){
            Vars.config.waitTime = Long.valueOf(time);
            configService.updateConfig();
            saveWaitTimeTip.setText("已保存");
        }
        else {
            saveWaitTimeTip.setText("数值只能为>=0的整数");
        }
    }
}
