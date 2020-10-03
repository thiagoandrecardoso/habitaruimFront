package com.habitarium.controller.register;

import com.habitarium.dao.PropertyDAO;
import com.habitarium.entity.Property;
import com.habitarium.utility.screen.AlertScreens;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPropertyScreenController implements Initializable {

    @FXML
    private ChoiceBox<String> chooseTypeProperty;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtNeighbour;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCondo;
    @FXML
    private TextField txtApartment;
    @FXML
    private TextField txtBlockCondo;
    @FXML
    private Button btnSave;
    Property property;

    @FXML
    void save() {
        if (checkPadding()) {
            property = new Property();
            property.setBlockCondo(txtBlockCondo.getText().trim());
            property.setCity(txtCity.getText().trim());
            property.setCondo(txtCondo.getText().trim());
            property.setNeighbour(txtNeighbour.getText().trim());
            property.setPropertyNumber(txtNumber.getText().trim());
            property.setStreet(txtStreet.getText().trim());
            property.setApartment(txtApartment.getText().trim());

            PropertyDAO propertyDAO = new PropertyDAO();
            property = propertyDAO.save(property);

            AlertScreens.alertConfirmation("", "Propriedade salva com sucesso!");

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        } else {
            AlertScreens.alertError("Há campos em branco","Erro ao preencher");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChooseTypeProperty();
        disableTextField();
    }

    public void setChooseTypeProperty() {
        chooseTypeProperty.setItems(FXCollections.observableArrayList(
                "Apartamento", "Casa", "Condomínio")
        );

        chooseTypeProperty.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            if (new_value.intValue() == 0) {
                txtCity.setDisable(false);
                txtNeighbour.setDisable(false);
                txtNumber.setDisable(false);
                txtStreet.setDisable(false);
                txtApartment.setDisable(false);
                txtCondo.setDisable(false);
                txtBlockCondo.setDisable(false);
            } else if (new_value.intValue() == 1) {
                txtApartment.setDisable(true);
                txtCondo.setDisable(true);
                txtBlockCondo.setDisable(true);
                txtCity.setDisable(false);
                txtNeighbour.setDisable(false);
                txtNumber.setDisable(false);
                txtStreet.setDisable(false);
                txtCondo.setText("");
                txtBlockCondo.setText("");
                txtApartment.setText("");
            } else if (new_value.intValue() == 2) {
                txtCity.setDisable(false);
                txtNeighbour.setDisable(false);
                txtNumber.setDisable(false);
                txtStreet.setDisable(false);
                txtApartment.setDisable(true);
                txtCondo.setDisable(false);
                txtBlockCondo.setDisable(false);
                txtApartment.setText("");
            }
        });
    }

    public boolean checkPadding() {
        boolean isApartment = !txtApartment.getText().trim().equals("") && !txtCondo.getText().trim().equals("")
                && !txtBlockCondo.getText().trim().equals("") && !txtCity.getText().trim().equals("")
                && !txtNeighbour.getText().trim().equals("") && !txtNumber.getText().trim().equals("")
                && !txtStreet.getText().trim().equals("")
                && chooseTypeProperty.getSelectionModel().getSelectedIndex() == 0;

        boolean isHouse = !txtCity.getText().trim().equals("")
                && !txtNeighbour.getText().trim().equals("") && !txtNumber.getText().trim().equals("")
                && !txtStreet.getText().trim().equals("")
                && chooseTypeProperty.getSelectionModel().getSelectedIndex() == 1;

        boolean isCondo = !txtCondo.getText().trim().equals("")
                && !txtBlockCondo.getText().trim().equals("") && !txtCity.getText().trim().equals("")
                && !txtNeighbour.getText().trim().equals("") && !txtNumber.getText().trim().equals("")
                && !txtStreet.getText().trim().equals("")
                && chooseTypeProperty.getSelectionModel().getSelectedIndex() == 2;

        return isApartment || isHouse || isCondo;
    }

    public void disableTextField() {
        txtApartment.setDisable(true);
        txtCondo.setDisable(true);
        txtBlockCondo.setDisable(true);
        txtCity.setDisable(true);
        txtNeighbour.setDisable(true);
        txtNumber.setDisable(true);
        txtStreet.setDisable(true);
    }
}
