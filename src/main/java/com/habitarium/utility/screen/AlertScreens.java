package main.java.com.habitarium.utility.screen;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import main.java.com.habitarium.utility.Icon;

public class AlertScreens {

    public static void alert(Alert.AlertType alertType, String contentText, String headerText) {
        Alert alert = new Alert(alertType, contentText, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Icon.setIcon(stage, "/main/resources/com/habitarium/icon.png");
        alert.setTitle("");
        alert.setHeaderText(headerText);
        alert.show();
    }

    public static void alertError(String contentText, String headerText) {
        alert(Alert.AlertType.ERROR, contentText, headerText);
    }

    public static void alertConfirmation(String contentText, String headerText) {
        alert(Alert.AlertType.CONFIRMATION, contentText, headerText);
    }
}
