package com.habitarium.controller.search;


import com.habitarium.utility.screen.OpenEditPropertyScreen;
import com.habitarium.utility.screen.OpenScreens;
import com.habitarium.utility.screen.Reloadable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.dao.PropertyDAO;
import main.java.entity.Property;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SearchPropertyScreenController implements Initializable, Reloadable {

    @FXML
    public TextField tfSearch;
    @FXML
    public Button btnSearch;
    @FXML
    public ListView<Property> listViewPane;
    private ObservableList<Property> propertyObservableList;
    private ObservableList<Property> searchResult;
    private OpenScreens openScreens;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListViewPane();
        openScreens = new OpenEditPropertyScreen();
        openScreens.setReload(this);
        searchProperty();
    }

    public List<Property> searchListProperty(String searchStr) {
        List<Property> items = listViewPane.getItems();
        List<BoundExtractedResult<Property>> result = FuzzySearch.extractSorted(searchStr, items,
                Property::toString, 57);
        return result.stream().map(BoundExtractedResult::getReferent).collect(Collectors.toList());
    }

    private void searchProperty() {
        List<Property> items = listViewPane.getItems();
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            List<BoundExtractedResult<Property>> result = FuzzySearch.extractSorted(newValue, items,
                    Property::toString, 57);

            searchResult = FXCollections.observableList(result.stream()
                    .map(BoundExtractedResult::getReferent).collect(Collectors.toList()));
            listViewPane.setItems(searchResult);
            System.out.println(result);
            if (tfSearch.getText().length() == 0) {
                listViewPane.setItems(propertyObservableList);
            }
        });
    }

    public void setListViewPane() {
        PropertyDAO propertyDAO = new PropertyDAO();
        List<Property> propertyList = propertyDAO.getList();
        if (!propertyList.isEmpty()) {
            propertyObservableList = FXCollections.observableList(propertyList);
            listViewPane.setItems(propertyObservableList);
        }
    }

    @FXML
    private void eventOpenEditProperties() {
        if(listViewPane.getSelectionModel().getSelectedIndex() != -1){
            Property selectedItemProperty = listViewPane.getSelectionModel().getSelectedItem();
            try {
                openScreens.loadScreen("screen/edit/editProperty", "Editor de propriedade", selectedItemProperty);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onActionBtnSearch() {
        propertyObservableList = FXCollections.observableList(searchListProperty(tfSearch.getText()));
        listViewPane.setItems(propertyObservableList);
    }

    @Override
    public void reload() {
        listViewPane.getItems().clear();
        setListViewPane();
    }
}
