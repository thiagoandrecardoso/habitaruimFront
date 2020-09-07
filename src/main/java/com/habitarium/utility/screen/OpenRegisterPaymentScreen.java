package main.java.com.habitarium.utility.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.habitarium.App;
import main.java.com.habitarium.controller.register.RegisterPaymentController;
import main.java.entity.Rent;

import java.io.IOException;
import java.net.URL;

public class OpenRegisterPaymentScreen implements OpenScreens {
    Reloadable reloadable;
    @Override
    public void loadScreen(String screen, String title, Object object) throws IOException {
        Rent rent = (Rent) object;
        FXMLLoader fxmlLoader;
        URL url = App.class.getResource(screen + ".fxml");
        if (url == null) {
            throw new IOException("File \"" + screen + ".fxml\" doesn't exists.");
        } else {
            fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            RegisterPaymentController registerPaymentController = fxmlLoader.getController();
            registerPaymentController.initializeScreen(rent);
            Stage stage = new Stage();
//            Icon.setIcon(stage, "icon.png");
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
