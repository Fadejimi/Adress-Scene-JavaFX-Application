/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Test
 */
public class PersonOverviewController {
    
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    
    
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    
    private DateUtil utils;
    private MainApp mainApp;
    
    public PersonOverviewController()
    {
        utils = new DateUtil();
    }
    
    @FXML
    private void initialize()
    {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        // Auto resize column
        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // clear person
        showPersonDetails(null);
        
        // listen for selection changes
        personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }
    
    @FXML
    private void handleDeletePerson()
    {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
        {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table");
            
            alert.showAndWait();
        }
    }
    
    public void showPersonDetails(Person person)
    {
        if (person != null)
        {
            firstNameLabel.setText(person.getFirstname());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());
            postalCodeLabel.setText(person.getPostalCode() + "");
            birthdayLabel.setText(utils.format(person.getBirthday()));
        }
        else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
            postalCodeLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    /**
     * Called on when clicks the new button
     * to add a user  
     */
    @FXML
    public void handleNewPerson()
    {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }
    
    @FXML
    public void handleEditPerson()
    {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked)
            {
                refreshPersonTable();
                showPersonDetails(selectedPerson);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No person selected");
            alert.setContentText("Please select a person on the table");
            
            alert.showAndWait();
            
        }
    }
    
    public void refreshPersonTable()
    {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        
        personTable.setItems(null);
        personTable.layout();
        personTable.setItems(mainApp.getPersonData());
        personTable.getSelectionModel().select(selectedIndex);
    }
    
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
        
        // add observablelist data to the table
        personTable.setItems(mainApp.getPersonData());
    }
}
