/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address;

/**
 *
 * @author Test
 */
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField streetField;
    
    @FXML 
    private TextField postalCodeField;
    
    @FXML
    private TextField cityField;
    
    @FXML
    private TextField birthdayField;
    
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
    
    private void initialize() {
        
    }
    
    /**
     * set the stage of this dialog
     */
    public void setStageDialog(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
    
    
    public void setPerson(Person person)
    {
        this.person = person;
        
        firstNameField.setText(person.getFirstname());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(person.getPostalCode() + "");
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    } 
    
    public boolean isOKClicked()
    {
        return this.okClicked;
    }
    
    @FXML
    public void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setCity(cityField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            
            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleCancel()
    {
        dialogStage.close();
    }
    
    /**
     * Validates the user input
     */
    public boolean isInputValid()
    {
        String errorMessage = "";
        
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0)
        {
            errorMessage += "No valid first name\n";
        }
        
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0)
        {
            errorMessage += "No valid last name\n";
        }
        
        if (streetField.getText() == null || streetField.getText().length() == 0)
        {
            errorMessage += "No valid street\n";
        }
        
        if (cityField.getText() == null || cityField.getText().length() == 0)
        {
            errorMessage += "No valid city\n";
        }
        
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0)
        {
            errorMessage += "No valid postal code\n";
        } else {
            try {
                Integer.parseInt(postalCodeField.getText());
            }
            catch(NumberFormatException ex)
            {
                errorMessage += "No valid postal code (must be integer)\n";
            }
        }
        
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0)
        {
            errorMessage += "No valid birthday\n";
        } else {
            if (!DateUtil.validString(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format yyyy-mm-dd!\n";
            }
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please Correct Invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}
