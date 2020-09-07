package com.habitarium.utility;

import com.habitarium.App;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Icon {
    public static void setIcon(Stage stage, String path) {
        stage.getIcons().add(new Image(App.class.getResourceAsStream(path)));
    }
}
