package gui.controller;

import be.Event;
import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

public class EditEventController implements Initializable {

    @FXML
    private ImageView eventImagePreview;
    @FXML
    private TextField eeImage;
    @FXML
    private MFXDatePicker datePickerEditEvent;
    @FXML
    private MFXComboBox<Integer> editTimeCBH, editTimeCBM, editExitTimeCBH, editExitTimeCBM;

    @FXML
    private TextField editLocationTF, editNameTF;
    @FXML
    private TextArea editNotesTF, editLocationInfoTF;

    @FXML
    private Label editErrorLabel;

    @FXML
    private Button editEventButton, editCancelButton;

    private String selectedFile = " ";

    private Event eventToBeEdited;
    private Model model = Model.getModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.loadTime();

        editTimeCBH.setItems(model.getHoursTime());
        editTimeCBM.setItems(model.getMinsTime());
        editExitTimeCBH.setItems(model.getHoursTime());
        editExitTimeCBM.setItems(model.getMinsTime());

        editErrorLabel.setText("Required fields are marked with *.");
    }

    /**
     * This method controls what we are doing with the chosen image file by the user.
     * We move the file to a local folder .../resources/EventImages/FILENAME
     */
    public void imageFileExplorer(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            setFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(new Stage());

            try{
                Path movefrom = FileSystems.getDefault().getPath(file.getPath());
                Path target = FileSystems.getDefault().getPath("resources/EventImages/"+file.getName());
                Files.move(movefrom,target, StandardCopyOption.ATOMIC_MOVE);
                selectedFile = target.toString();
                eeImage.setText(file.getName());

                Image previewImage = new Image(new FileInputStream("resources/EventImages/"+file.getName()));
                eventImagePreview.setImage(previewImage);
            }catch (NullPointerException n){
                System.out.println("File move failed.");
            }


        } catch(IOException e){
            System.out.println("File selection has failed");
        }
    }

    /**
     * Mehod to configure the file chooser and select which file type are accepted
     */
    private static void setFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select event Image");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.bmp", "*.png", "*.jpeg")
        );
    }

    /**
     * We take all user input information and pass it forward to ultimately update our event with any changes.
     */
    private void editEventInformation(){

        Time startTime = null;
        Time endTime = new Time(0,0,0); // The end time is automatically set for 00:00:00 if there is no time selection

        if(editExitTimeCBM.getSelectedItem() != null && editExitTimeCBH.getSelectedItem() != null){
            endTime = new Time(geteTimeCBH(), geteTimeCBM(), 0);
        }

        if(editTimeCBH.getSelectedItem() != null && editTimeCBM.getSelectedItem() != null){
            startTime = new Time(getTimeCBH(), getTimeCBM(), 0);
        }



        if(!getNeNameTF().isEmpty()&& !getNeLocationTF().isEmpty() && !getNeNotesTF().isEmpty() && startTime != null) {
            eventToBeEdited.setDate(getDatePickerNewEvent());
            eventToBeEdited.setLocation(getNeLocationTF());
            eventToBeEdited.setTime(startTime);
            eventToBeEdited.setEndTime(endTime);
            eventToBeEdited.setName(getNeNameTF());
            eventToBeEdited.setNotes(getNeNotesTF());
            eventToBeEdited.setLocationGuidance(getNeLocationInfoTF());
            eventToBeEdited.setImageFilePath(selectedFile);
            model.updateEvent(eventToBeEdited);
            editErrorLabel.setText("Event edited!");
            closeWindow();
        }else editErrorLabel.setText("Edit failed. Please fill all fields marked with *.");


    }

    public void setEditEventValues(Event editEvent){// we use this method to set our fields with the information that is already associated with the event.
        datePickerEditEvent.setValue(editEvent.getDate().toLocalDate());
        editLocationTF.setText(editEvent.getLocation());
        editLocationInfoTF.setText(editEvent.getLocationGuidance());
        editNameTF.setText(editEvent.getName());
        editNotesTF.setText(editEvent.getNotes());
        eeImage.setText(editEvent.getImageFilePath());

        editTimeCBH.selectItem(editEvent.getTime().toLocalTime().getHour());
        editTimeCBM.selectItem(editEvent.getTime().toLocalTime().getMinute());

        if(editEvent.getEndTime() != null){
        editExitTimeCBH.selectItem(editEvent.getEndTime().toLocalTime().getHour());
        editExitTimeCBM.selectItem(editEvent.getEndTime().toLocalTime().getMinute());
        }
    }

    private void closeWindow(){
        Stage stage = (Stage) editCancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void editEvent(ActionEvent actionEvent) {
        editEventInformation();
    }
    @FXML
    private void cancelEditWindow(ActionEvent actionEvent) {
        closeWindow();
    }

    public void setEvent(Event event) {
        this.eventToBeEdited = event;
    }

    private Date getDatePickerNewEvent() {return Date.valueOf(datePickerEditEvent.getValue());}
    private int getTimeCBH() {return editTimeCBH.getSelectedItem();}
    private int getTimeCBM() {return editTimeCBM.getSelectedItem();}
    private int geteTimeCBH() {return editExitTimeCBH.getSelectedItem();}
    private int geteTimeCBM() {return editExitTimeCBM.getSelectedItem();}
    private String getNeNameTF() {return editNameTF.getText();}
    private String getNeLocationTF() {return editLocationTF.getText();}
    private String getNeNotesTF() {return editNotesTF.getText();}
    private String getNeLocationInfoTF() {return editLocationInfoTF.getText();}

    public TextField getEeImage() {
        return eeImage;
    }
}
