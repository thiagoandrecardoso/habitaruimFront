package main.java.com.habitarium.controller.edit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.com.habitarium.utility.screen.AlertScreens;
import main.java.dao.PropertyDAO;
import main.java.entity.Property;


public class EditPropertyController {

    @FXML
    public TextField tfStreet;
    @FXML
    public TextField tfNumber;
    @FXML
    public TextField tfNeighbour;
    @FXML
    public TextField tfCity;
    @FXML
    public TextField tfCondo;
    @FXML
    public TextField tfApartment;
    @FXML
    public TextField tfBlockCondo;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnDelete;

    private Property property;
    private final PropertyDAO propertyDAO = new PropertyDAO();

    public void initializeScreen(Property property) {
        this.property = property;
        initTextFields(property);
        initDisabled();
    }

    public void initTextFields(Property property) {
        tfStreet.setText(property.getStreet());
        tfNumber.setText((property.getPropertyNumber()));
        tfNeighbour.setText(property.getNeighbour());
        tfCity.setText((property.getCity()));
        tfCondo.setText(property.getCondo());
        tfApartment.setText(property.getApartment());
        tfBlockCondo.setText(property.getBlockCondo());
    }

    @FXML
    private void save() {
        if (checkPadding()) {
            property.setBlockCondo(tfBlockCondo.getText().trim());
            property.setCity(tfCity.getText().trim());
            property.setCondo(tfCondo.getText().trim());
            property.setNeighbour(tfNeighbour.getText().trim());
            property.setPropertyNumber(tfNumber.getText().trim());
            property.setStreet(tfStreet.getText().trim());
            property.setApartment(tfApartment.getText().trim());

            propertyDAO.update(property);
            Platform.runLater(() -> AlertScreens.alertConfirmation("", "Propriedade Atualizada!"));

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        } else {
            AlertScreens.alertError("Há campos em branco","Erro ao preencher");
        }
    }

    @FXML
    private void delete() {
        if (property.getRent() == null) {
            propertyDAO.delete(property.getId());

            AlertScreens.alertConfirmation("", "Propriedade Deletada!");

            Stage stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();
        } else {
            AlertScreens.alertError("Propriedade alugada", "Propriedade não pode ser deletada!");
        }
    }

    private void initDisabled() {
        tfApartment.setDisable(property.getApartment().equals(""));
        tfCondo.setDisable(property.getCondo().equals(""));
        tfBlockCondo.setDisable(property.getBlockCondo().equals(""));
    }

    public boolean checkPadding() {
        boolean isApartment = !tfApartment.getText().trim().equals("") && !tfCondo.getText().trim().equals("")
                && !tfBlockCondo.getText().trim().equals("") && !tfCity.getText().trim().equals("")
                && !tfNeighbour.getText().trim().equals("") && !tfNumber.getText().trim().equals("")
                && !tfStreet.getText().trim().equals("");

        boolean isHouse = !tfCity.getText().trim().equals("")
                && !tfNeighbour.getText().trim().equals("") && !tfNumber.getText().trim().equals("")
                && !tfStreet.getText().trim().equals("");

        boolean isCondo = !tfCondo.getText().trim().equals("")
                && !tfBlockCondo.getText().trim().equals("") && !tfCity.getText().trim().equals("")
                && !tfNeighbour.getText().trim().equals("") && !tfNumber.getText().trim().equals("")
                && !tfStreet.getText().trim().equals("");

        return isApartment || isHouse || isCondo;
    }
}
