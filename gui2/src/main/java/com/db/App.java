package com.db;

import com.db.view.IndexView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(App.class, IndexView.class, args);
    }

}
