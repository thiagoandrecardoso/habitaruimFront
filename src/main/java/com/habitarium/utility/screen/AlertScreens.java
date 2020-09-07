package com.habitarium.utility.screen;

import com.habitarium.utility.Icon;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AlertScreens {

    public static void alert(Alert.AlertType alertType, String contentText, String headerText) {
        Alert alert = new Alert(alertType, contentText, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Icon.setIcon(stage, "icon.png");
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
