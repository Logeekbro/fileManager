package com.db.controller;

import com.db.App;
import com.db.common.Vars;
import com.db.service.ConfigService;
import com.db.view.SettingView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

@FXMLController
public class IndexController implements Initializable {

    @Resource
    private ConfigService configService;

    @Resource
    private SettingView settingView;

    @Resource
    private SettingController settingController;

    @FXML
    private ListView<HBox> selected;

    @FXML
    private ListView<HBox> watched;

    private final Set<String> selectedFolders = new LinkedHashSet<>();

    private String path = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configService.readConfig();
        updateWatched();
    }


    @FXML
    protected void selectFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择一个文件夹");
        chooser.setInitialDirectory(new File(path == null ? Vars.rootDir : path));
        File newFolder = chooser.showDialog(App.getStage());
        if(newFolder == null){
            return;
        }
        path = newFolder.getAbsolutePath();
        if (this.selectedFolders.contains(path) || Vars.config.watchFolders.contains(path)){
            return;
        }
        this.selectedFolders.add(path);
        updateSelected();
    }

    @FXML
    protected void saveFolderToWatch() {
        this.selected.getItems().clear();
        Vars.config.watchFolders.addAll(this.selectedFolders);
        configService.updateConfig();
        updateWatched();
    }


    private void updateSelected(){
        Text text = new Text(path + "\t\t");
        text.setFont(Font.font(20.0));
        Button button = new Button("x");
        button.setId(path);
        button.autosize();
        button.setOnAction(event -> {
            Button button1 = (Button) event.getSource();
            HBox buttonParent = (HBox) button1.getParent();
            this.selected.getItems().remove(buttonParent);
            this.selectedFolders.remove(button1.getId());
        });
        HBox hBox = new HBox(text, button);
        this.selected.getItems().add(hBox);
    }

    private void updateWatched(){
        this.watched.getItems().clear();
        for(String i : Vars.config.watchFolders){
            Text text = new Text(i + "\t\t");
            text.setFont(Font.font(20.0));
            Button delButton = new Button("X");
            delButton.setId(i);
            delButton.setOnAction(event -> {
                Button button = (Button) event.getSource();
                HBox buttonParent = (HBox) button.getParent();
                this.watched.getItems().remove(buttonParent);
                this.selectedFolders.remove(button.getId());
                Vars.config.watchFolders.remove(button.getId());
                configService.updateConfig();
            });
            HBox hBox = new HBox(text, delButton);
            this.watched.getItems().add(hBox);
        }
    }

    @FXML
    public void toSetting(){
        Stage newStage = new Stage();
        Scene newScene;
        if (settingView.getView().getScene() != null) {
            newScene = settingView.getView().getScene();
        } else {
            newScene = new Scene(settingView.getView());
        }
        newStage.setScene(newScene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(App.getStage());
        newStage.setTitle("设置");
        newStage.initStyle(StageStyle.UNIFIED);
        newStage.setOnCloseRequest(
                (event) -> settingController.init()
        );
        newStage.showAndWait();
    }
}
