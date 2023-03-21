package gui.controller;

import be.Event;
import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

public class EditEventController implements Initializable {

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
            model.updateEvent(eventToBeEdited);
            editErrorLabel.setText("Event edited!");
            closeWindow();
        }else editErrorLabel.setText("Edit failed. Please fill all fields marked with *.");


    }

    public void setEditEventValues(Event editEvent){
        datePickerEditEvent.setValue(editEvent.getDate().toLocalDate());
        editLocationTF.setText(editEvent.getLocation());
        editLocationInfoTF.setText(editEvent.getLocationGuidance());
        editNameTF.setText(editEvent.getName());
        editNotesTF.setText(editEvent.getNotes());

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
}
