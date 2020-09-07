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

public class OpenSearchRentScreen implements OpenScreens{
    private Reloadable reloadable;
    @Override
    public void loadScreen(String screen, String title, Object object) throws IOException {
        FXMLLoader fxmlLoader;
        URL url = App.class.getResource(screen + ".fxml");
        if (url == null) {
            throw new IOException("File \"" + screen + ".fxml\" doesn't exists.");
        } else {
            fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Icon.setIcon(stage, "icon.png");
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHiding(e -> reloadable.reload());
            stage.show();
        }
    }

    @Override
    public void setReload(Reloadable reloadable) {
        this.reloadable = reloadable;
    }
}
