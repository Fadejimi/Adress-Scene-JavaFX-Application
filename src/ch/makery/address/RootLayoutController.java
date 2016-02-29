/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 *
 * @author Test
 */
public class RootLayoutController {
    
    // Reference the main application
    private MainApp mainApp;
    
    /**
     * Is called by the main application to give reference to itself
     * 
     */
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
    
    /**
     * Creates an empty address book
     */
    @FXML
    private void handleNew()
    {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }
    
    /**
     * Opens a filechooser to enable the user select the address book he wants to load
     */
    @FXML
    private void handleOpen()
    {
        FileChooser fileChooser = new FileChooser();
        
        // set extention filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // show the save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            mainApp.loadPersonsDataFromFile(file);
        }
    }
    
    @FXML
    private void handleSave()
    {
        File personFile = mainApp.getPersonFilePath();
        
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }
    
    @FXML
    private void handleSaveAs()
    {
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        
        fileChooser.getExtensionFilters().add(extFilter);
        
        // show file save dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            // Make sure it has the same extention
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }
    
    @FXML
    private void handleAbout()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Adegbulugbe Fadejimi");
        
        alert.showAndWait();
    }
    
    
    @FXML
    private void handleExit()
    {
        System.exit(0);
    }
    
    @FXML
    private void handleStatistics()
    {
        mainApp.showBirthdayStatistics();
    }
}
