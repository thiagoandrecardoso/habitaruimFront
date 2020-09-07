package com.habitarium.controller.edit;

import com.habitarium.utility.date.DateUtil;
import com.habitarium.utility.screen.AlertScreens;
import com.habitarium.utility.screen.OpenRegisterPaymentScreen;
import com.habitarium.utility.screen.OpenScreens;
import com.habitarium.utility.screen.Reloadable;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.java.controller.MonthPaidController;
import main.java.controller.RentController;
import main.java.dao.LessorDAO;
import main.java.dao.PropertyDAO;
import main.java.dao.RentDAO;
import main.java.entity.Lessor;
import main.java.entity.MonthPaid;
import main.java.entity.Property;
import main.java.entity.Rent;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditRentController implements Initializable, Reloadable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCpf;
    @FXML
    private TextField tfTel1;
    @FXML
    private TextField tfTel2;
    @FXML
    private ListView<MonthPaid> lvMonthPaid;
    @FXML
    private TextField tfRg;
    @FXML
    private TextField tfValue;
    @FXML
    private DatePicker dpEntranceDate;
    @FXML
    private DatePicker dpReadjustment;
    @FXML
    private DatePicker dpExitDate;
    @FXML
    private Spinner<Integer> spPayDay;
    @FXML
    private Button btnSave;
    @FXML
    private TextField tfProperty;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnMakePayment;

    private Rent rent;
    private Lessor lessor;
    private List<MonthPaid> monthsPaid;
    private final String PATTERN_MATCHES_RENT_VALUE = "[0-9,]";
    private final int RENT_VALUE_LENGTH = 10;
    OpenScreens openScreens;
    private final RentDAO rentDAO = new RentDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTfValueFilter();
        openScreens = new OpenRegisterPaymentScreen();
        openScreens.setReload(this);
    }

    public void initializeScreen(Rent rent) {
        this.rent = rent;
        this.lessor = rent.getLessor();
        this.monthsPaid = rent.getMonthPaidList();
        initTextFields();
        setSpPayDay();
        setDatePickers();
        setLvMonthPaid();
    }

    public void setLvMonthPaid() {
        lvMonthPaid.setItems(FXCollections.observableList(monthsPaid.stream()
                .filter(MonthPaid::isPaid)
                .collect(Collectors.toList())));
    }

    public void setDatePickers() {
        dpEntranceDate.valueProperty().setValue(Instant.ofEpochMilli(rent.getEntranceDate().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate());
        dpExitDate.valueProperty().setValue(Instant.ofEpochMilli(rent.getExitDate().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate());
        dpReadjustment.valueProperty().setValue(Instant.ofEpochMilli(rent.getReadjustmentDate().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public void setSpPayDay() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.
                IntegerSpinnerValueFactory(1, DateUtil.lastDayCurrentMonth(), rent.getPayDay());
        spPayDay.setValueFactory(valueFactory);
        spPayDay.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                spPayDay.getValueFactory().setValue(oldValue);
            }
        });
    }

    public void initTextFields() {
        tfName.setText(lessor.getName());
        tfCpf.setText(lessor.getCpf());
        tfRg.setText(lessor.getRg());
        tfTel1.setText(lessor.getTelOne());
        tfTel2.setText(lessor.getTelTwo());
        tfProperty.setText(rent.getProperty().toString());
        tfValue.setText(String.valueOf(rent.getValue()));
    }

    @FXML
    private void save() {
        MonthPaidController monthPaidController = new MonthPaidController();
        RentController rentController = new RentController();
        Rent oldRent = rentController.copyRent(rent);

        if (checkTxtPadding()) {
            lessor.setName(tfName.getText().trim());
            lessor.setCpf(tfCpf.getText().trim());
            lessor.setRg(tfRg.getText().trim());
            lessor.setTelOne(tfTel1.getText().trim());
            lessor.setTelTwo(tfTel2.getText().trim());

            rent.setLessor(lessor);
            rent.setValue(Float.parseFloat(tfValue.getText().trim()));
            rent.setPayDay(spPayDay.getValue());

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                rent.setEntranceDate(format.parse(dpEntranceDate.getEditor().getText().trim()));
                rent.setExitDate(format.parse(dpExitDate.getEditor().getText().trim()));
                rent.setReadjustmentDate(format.parse(dpReadjustment.getEditor().getText().trim()));

                if (rentController.hasChangedDatesOrValue(oldRent, rent)) {
                    monthPaidController.deleteAll(rent.getMonthPaidList());
                    rent.setMonthPaidList(rentController.setMonthsToPay(rent));
                }
                rentDAO.update(rent);
                AlertScreens.alertConfirmation("", "Aluguel Atualizado!");

                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            } catch (ParseException e) {
                AlertScreens.alertError("Data inválida", "Erro de data");
                e.printStackTrace();
            }

        } else {
            AlertScreens.alertError("Há campos em branco", "Erro ao preencher");
        }
    }

    @FXML
    private void delete() {
        PropertyDAO propertyDAO = new PropertyDAO();
        Property property = propertyDAO.findById(rent.getProperty().getId());
        property.setRent(null);

        LessorDAO lessorDAO = new LessorDAO();
        lessorDAO.delete(rent.getLessor().getId());

        rentDAO.delete(rent.getId());
        AlertScreens.alertConfirmation("", "Aluguel Deletado!");
        Stage stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registerPayment() {
        try {
            openScreens.loadScreen("/main/resources/com/habitarium/screen/register/registerPayment", "Registrar Pagamento", rent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkTxtPadding() {
        boolean registerLessor = !tfName.getText().trim().equals("") && !tfCpf.getText().trim().equals("")
                && !tfRg.getText().trim().equals("") && !tfTel1.getText().trim().equals("")
                && !tfTel2.getText().trim().equals("");
        boolean hasSpinnerValue = spPayDay.getValue() != null;

        return registerLessor && hasSpinnerValue;
    }

    private void setTfValueFilter() {
        tfValue.addEventFilter(KeyEvent.KEY_TYPED, getPatternValidation(PATTERN_MATCHES_RENT_VALUE));
        tfValue.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.length() > RENT_VALUE_LENGTH) {
                tfValue.setText(oldValue);
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

    @Override
    public void reload() {
        setLvMonthPaid();
    }
}
