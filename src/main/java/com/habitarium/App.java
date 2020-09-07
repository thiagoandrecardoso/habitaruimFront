package com.habitarium;

import com.habitarium.utility.Icon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("screen/loginScreen"));
        stage.setScene(scene);
        Icon.setIcon(stage, "icon.png");
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }

    private static Parent loadFXML(String screen) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(screen + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}