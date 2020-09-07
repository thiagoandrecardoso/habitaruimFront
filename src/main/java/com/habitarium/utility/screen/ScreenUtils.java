package com.habitarium.utility.screen;

import com.habitarium.App;
import com.habitarium.utility.Icon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ScreenUtils {

    private static ScreenUtils screenUtilsInstance;
    private ScreenUtils() {
    }

    public static void switchScreen(String screen, String title) throws IOException {
        URL url = App.class.getResource(screen + ".fxml");
        if (url == null) {
            throw new IOException("File \"" + screen + ".fxml\" doesn't exists.");
        } else{
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Icon.setIcon(stage, "icon.png");
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    public static synchronized ScreenUtils getInstance() {
        if (screenUtilsInstance == null) {
            screenUtilsInstance = new ScreenUtils();
        }
        return screenUtilsInstance;
    }
}
