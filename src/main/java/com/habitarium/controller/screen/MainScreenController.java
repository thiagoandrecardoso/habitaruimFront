package com.habitarium.controller.screen;


import com.habitarium.utility.screen.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import com.habitarium.controller.MonthPaidController;
import com.habitarium.dao.RentDAO;
import com.habitarium.entity.MonthPaid;
import com.habitarium.entity.Rent;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable, Reloadable {

    @FXML
    private Button registerPropertyBtn;
    @FXML
    private Button registerRentBtn;
    @FXML
    private Button searchRentBtn;
    @FXML
    private Button searchPropertyBtn;
    @FXML
    private ListView<MonthPaid> lvDebtors;
    @FXML
    private Button btnInfo;

    private OpenScreens openScreen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDebtorsListView();
    }

    private void initDebtorsListView() {
        lvDebtors.setItems(getMonthsPaid());
    }

    private ObservableList<MonthPaid> getMonthsPaid() {
        RentDAO rentDAO = new RentDAO();
        MonthPaidController monthPaidController = new MonthPaidController();
        ObservableList<MonthPaid> result = FXCollections.observableArrayList();
        List<Rent> rents = rentDAO.getList();
        for (Rent rent : rents) {
            result.addAll(monthPaidController.lateMonthsInRange(rent.getMonthPaidList(), rent.getEntranceDate(),
                    new Date()));
        }
        return result;
    }

    @FXML
    public void registerProperty() {
        try {
            ScreenUtils.switchScreen("screen/register/registerPropertyScreen", "Registro de Propriedade");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerRent() {
        openScreen = new OpenRegisterRentScreen();
        openScreen.setReload(this);
        try {
            openScreen.loadScreen("screen/register/registerRentScreen", "Registro de Aluguel", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setBtnInfo() {
        try {
            ScreenUtils.switchScreen("screen/utils/developInfo", "Informações dos Desenvolvedores");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchProperty() {
        openScreen = new OpenSearchPropertyScreen();
        try {
            openScreen.loadScreen("screen/search/searchProperty", "Procura de Propriedades", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchRent() {
        openScreen = new OpenSearchRentScreen();
        openScreen.setReload(this);
        try {
            openScreen.loadScreen("screen/search/searchRent", "Procura de Aluguéis", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openEditRent() {
        if (lvDebtors.getSelectionModel().getSelectedIndex() != -1) {
            Rent selectedItemRent = lvDebtors.getSelectionModel().getSelectedItem().getRent();
            openScreen = new OpenEditRentScreen();

            openScreen.setReload(this);
            try {
                openScreen.loadScreen("screen/edit/editRent", "Editor de Aluguéis", selectedItemRent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reload() {
        lvDebtors.setItems(getMonthsPaid());
    }
}
