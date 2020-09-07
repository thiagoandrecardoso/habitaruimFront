package main.java.com.habitarium.utility.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.habitarium.App;
import main.java.com.habitarium.controller.screen.FirstLoginScreenController;
import main.java.com.habitarium.utility.Icon;
import main.java.entity.User;

import java.io.IOException;
import java.net.URL;

public class OpenFirstLoginScreen implements OpenScreens {
    @Override
    public void loadScreen(String screen, String title, Object object) throws IOException {
        User user = (User) object;
        FXMLLoader fxmlLoader;
        URL url = App.class.getResource(screen + ".fxml");
        if (url == null) {
            throw new IOException("File \"" + screen + ".fxml\" doesn't exists.");
        } else {
            fxmlLoader = new FXMLLoader(url);
            FXMLLoader loader = fxmlLoader;
            Parent root = loader.load();
            FirstLoginScreenController firstLoginScreenController = loader.getController();
            firstLoginScreenController.setUser(user);
            Stage stage = new Stage();
//            Icon.setIcon(stage, "icon.png");
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @Override
    public void setReload(Reloadable reloadable) {
    }
}
