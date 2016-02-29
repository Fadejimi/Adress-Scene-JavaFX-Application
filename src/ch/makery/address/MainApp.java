/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.BirthdayStatisticsController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Test
 */
public class MainApp extends Application{
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    
    public MainApp()
    {
        personData.add(new Person("Hans", "Solo"));
        personData.add(new Person("Fadejimi", "Adegbulugbe"));
        personData.add(new Person("Timilehin", "Adegbulugbe"));
        personData.add(new Person("Segun", "Fajemisin"));
        personData.add(new Person("Richard", "Usang"));
        personData.add(new Person("Sade", "Demilade"));
        personData.add(new Person("Crimson", "Filler"));
        personData.add(new Person("Ibukun", "Adegbulugbe"));
        personData.add(new Person("Edna", "Eziekel"));
    }
    
    public ObservableList<Person> getPersonData()
    {
        return personData;
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address App");
        this.primaryStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
        
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // show the scene containing the rootLayout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // give the controller access to the main app
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        
        // try to load the last open file
        File file = getPersonFilePath();
        if (file != null){
            loadPersonsDataFromFile(file);
        }
        showPersonOverview();
    }
    
    public Stage getPrimaryStage()
    {
        return this.primaryStage;
    }
    
    /**
     * Shows the person overview
     */
    
    public void showPersonOverview()
    {
        try {
            // load the fxml file and set the center of the main layout
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(""
                    + "view/PersonOverview.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            
            // Give the controller access to the mainApp
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public boolean showPersonEditDialog(Person person)
    {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            
            // set the person into the controller
            PersonEditDialogController controller = loader.getController();
            controller.setStageDialog(dialogStage);
            controller.setPerson(person);
            
            // show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return controller.isOKClicked();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
    * Returns the person file preference, i.e. the file that was last opened.
    * The preference is read from the OS specific registry. If no such
    * preference can be found, null is returned.
    * 
    * @return
    */
    public File getPersonFilePath()
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        
        if (filePath != null){
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    /**
    * Sets the file path of the currently loaded file. The path is persisted in
    * the OS specific registry.
    * 
    * @param file the file or null to remove the path
    */
    public void setPersonFilePath(File file)
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null)
        {
            prefs.put("filePath", file.getPath());
            
            // Update the stage title
            this.primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
            
            // update the stage title
            this.primaryStage.setTitle("AddressApp");
        }
    }
    
    /**
     * Loads person data into the application
     * @param args 
     */
    public void loadPersonsDataFromFile(File file)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            
            // Reading XML from the file and unmarshalling it
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            
            personData.clear();
            personData.addAll(wrapper.getPersons());
            
            // Save the file path to the registry
            setPersonFilePath(file);
        } catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load from file");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    /**
     * Saves the current person data to specified file
     * @param args 
     */
    public void savePersonDataToFile(File file)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            // wrapping our person data
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);
            
            // Marshalling and saving XML to the file
            m.marshal(wrapper, file);
            
            // save the file path to the registry
            setPersonFilePath(file);
        } catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            
            alert.showAndWait();
        }
    }
        
    /**
     * Opens a dialog to show birthday statistics
     * @param args 
     */
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initOwner(primaryStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            
            // set the persons into the controller
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);
            
            dialogStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
