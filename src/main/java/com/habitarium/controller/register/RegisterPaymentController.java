package main.java.com.habitarium.controller.register;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.java.com.habitarium.utility.screen.AlertScreens;
import main.java.controller.MonthPaidController;
import main.java.dao.MonthPaidDAO;
import main.java.entity.MonthPaid;
import main.java.entity.Rent;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RegisterPaymentController implements Initializable {

    @FXML
    public ChoiceBox<String> choBAdvancePay;
    @FXML
    public ComboBox<String> cbOwedMonths;
    @FXML
    public TextField tfNewValue;
    @FXML
    public Button btnConfirmPayment;
    @FXML
    public RadioButton rbAnticipatePayment;

    private Rent rent;
    private final int RENT_VALUE_LENGTH = 10;
    private final String PATTERN_MATCHES_RENT_VALUE = "[0-9,]";
    private final MonthPaidController monthPaidController = new MonthPaidController();
    private List<MonthPaid> lateMonths;
    private List<MonthPaid> anticipateMonths;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTfNewValueFilter();
    }

    public void initializeScreen(Rent rent) {
        this.rent = rent;
        setRbAnticipatePayment();
        setChoBAdvancePay();
        setCbOwedMonths();
    }

    @FXML
    private void pay() {
        MonthPaidDAO monthPaidDAO = new MonthPaidDAO();
        MonthPaid selectedMonthPaid;

        if (!checkCbOwedMonthsIsSelected()) {
            AlertScreens.alertError("Nenhuma data selecionada!", "Erro");
            return;
        }

        if (!rbAnticipatePayment.isSelected()) {
            int index = cbOwedMonths.getSelectionModel().getSelectedIndex();
            selectedMonthPaid = lateMonths.get(index);
        } else {
            int index = choBAdvancePay.getSelectionModel().getSelectedIndex();
            selectedMonthPaid = anticipateMonths.get(index);
        }
        selectedMonthPaid.setValue(Float.parseFloat(tfNewValue.getText().trim()
                .replaceAll(",", ".")));
        selectedMonthPaid.setPaid(true);

        monthPaidDAO.update(selectedMonthPaid);
        AlertScreens.alertConfirmation("", "Pagamento realizado com sucesso!");

        Stage stage = (Stage) btnConfirmPayment.getScene().getWindow();
        stage.close();
    }

    public void setChoBAdvancePay() {
        anticipateMonths = monthPaidController.lateMonthsInRange(rent.getMonthPaidList(), new Date(),
                rent.getExitDate());
        List<String> anticipateStr = anticipateMonths.stream().map(MonthPaid::dateString).collect(Collectors.toList());
        choBAdvancePay.setItems(FXCollections.observableList(anticipateStr));
    }

    public void setCbOwedMonths() {
        lateMonths = monthPaidController.lateMonthsInRange(rent.getMonthPaidList(),
                rent.getEntranceDate(), new Date());
        List<String> monthsStr = lateMonths.stream().map(MonthPaid::dateString).collect(Collectors.toList());
        cbOwedMonths.setItems(FXCollections.observableList(monthsStr));
        cbOwedMonths.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("")) {
                    tfNewValue.setText(String.valueOf(rent.getValue()));
                }
            }
        });
    }

    public void setRbAnticipatePayment() {
        rbAnticipatePayment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (rbAnticipatePayment.isSelected()) {
                    choBAdvancePay.setDisable(false);
                    choBAdvancePay.getSelectionModel().selectFirst();
                    cbOwedMonths.setDisable(true);
                    tfNewValue.setText(String.valueOf(rent.getValue()));
                } else {
                    choBAdvancePay.setDisable(true);
                    choBAdvancePay.getSelectionModel().select(null);
                    cbOwedMonths.setDisable(false);
                    cbOwedMonths.getSelectionModel().selectFirst();
                }
            }
        });
    }

    public boolean checkCbOwedMonthsIsSelected() {
        return cbOwedMonths.getSelectionModel().getSelectedIndex() != -1 ||
                choBAdvancePay.getSelectionModel().getSelectedIndex() != -1;
    }

    public void setTfNewValueFilter() {
        tfNewValue.addEventFilter(KeyEvent.KEY_TYPED, getPatternValidation(PATTERN_MATCHES_RENT_VALUE));
        tfNewValue.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.length() > RENT_VALUE_LENGTH) {
                tfNewValue.setText(oldValue);
            }
        });
    }

    public static EventHandler<KeyEvent> getPatternValidation(String pattern) {
        return e -> {
            String typed = e.getCharacter();
            if (!typed.matches(pattern)) {
                e.consume();
            }
        };
    }
}
