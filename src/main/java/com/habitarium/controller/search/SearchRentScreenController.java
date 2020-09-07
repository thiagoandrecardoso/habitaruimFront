package com.habitarium.controller.search;


import com.habitarium.utility.screen.OpenEditRentScreen;
import com.habitarium.utility.screen.OpenScreens;
import com.habitarium.utility.screen.Reloadable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.dao.RentDAO;
import main.java.entity.Rent;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SearchRentScreenController implements Initializable, Reloadable {

    @FXML
    public TextField tfSearch;
    @FXML
    public Button btnSearch;
    @FXML
    public ListView<Rent> lvRent;

    private ObservableList<Rent> rentObservableList;
    private ObservableList<Rent> searchResult;
    private OpenScreens openEditRentScreens;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openEditRentScreens = new OpenEditRentScreen();
        openEditRentScreens.setReload(this);
        setListViewPane();
        searchRent();
    }

    public List<Rent> searchListRent(String searchStr){
        List<Rent> items = lvRent.getItems();
        List<BoundExtractedResult<Rent>> result = FuzzySearch.extractSorted(searchStr, items,
                Rent::toString, 57);
        return result.stream().map(BoundExtractedResult::getReferent).collect(Collectors.toList());
    }

    private void searchRent() {
        List<Rent> items = lvRent.getItems();
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            List<BoundExtractedResult<Rent>> result = FuzzySearch.extractSorted(newValue, items,
                    Rent::toString, 57);

            searchResult = FXCollections.observableList(result.stream()
                    .map(BoundExtractedResult::getReferent).collect(Collectors.toList()));
            lvRent.setItems(searchResult);
            System.out.println(result);
            if (tfSearch.getText().length() == 0) {
                lvRent.setItems(rentObservableList);
            }
        });
    }

    public void setListViewPane() {
        RentDAO rentDAO = new RentDAO();
        List<Rent> rentList = rentDAO.getList();
        if (!rentList.isEmpty()) {
            rentObservableList = FXCollections.observableList(rentList.stream()
                    .filter(r -> r.getLessor() != null && r.getProperty() != null)
                    .collect(Collectors.toList()));
            lvRent.setItems(rentObservableList);
        }
    }

    @FXML
    private void eventOpenEditRents() {
        if(lvRent.getSelectionModel().getSelectedIndex() != -1){
            Rent selectedItemRent = lvRent.getSelectionModel().getSelectedItem();
            try {
                openEditRentScreens.loadScreen("/main/resources/com/habitarium/screen/edit/editRent", "Editor de Aluguéis", selectedItemRent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onActionBtnSearch() {
        rentObservableList = FXCollections.observableList(searchListRent(tfSearch.getText()));
        lvRent.setItems(rentObservableList);
    }

    @Override
    public void reload() {
        lvRent.getItems().clear();
        setListViewPane();
    }
}
