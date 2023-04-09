package gui.controller;

import be.*;
import be.Event;
import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

public class NewEventViewController{
    @FXML
    private MFXDatePicker datePickerNewEvent;
    @FXML
    private MFXComboBox<Integer> timeCBH, timeCBM, eTimeCBH, eTimeCBM;
    @FXML
    private Label creationErrorLabel;
    @FXML
    private TextField neNameTF, neLocationTF;
    @FXML
    private TextArea neNotesTF, neLocationInfoTF;
    @FXML
    private Button neCreateButton, neCancelButton;


    private Model model = Model.getModel();


    @FXML
    private void createEvent(ActionEvent actionEvent) {
        createNewEvent();
    }
    @FXML
    private void cancelNewindow(ActionEvent actionEvent) {
        closeWindow();
    }


    private void closeWindow(){
        Stage stage = (Stage) neCancelButton.getScene().getWindow();
        stage.close();
    }

    private void createNewEvent(){

        Time startTime = null;
        Time endTime = new Time(0,0,0); // The end time is automatically set for 00:00:00 if there is no time selection

        if(eTimeCBM.getSelectedItem() != null && eTimeCBH.getSelectedItem() != null){
            endTime = new Time(geteTimeCBH(), geteTimeCBM(), 0);
        }

        if(timeCBH.getSelectedItem() != null && timeCBM.getSelectedItem() != null){
            startTime = new Time(getTimeCBH(), getTimeCBM(), 0);
        }

        Event e = null;

        if(!getNeNameTF().isEmpty()&& !getNeLocationTF().isEmpty() && !getNeNotesTF().isEmpty() && startTime != null) {
            e = new Event(getNeNameTF(), getNeLocationTF(), getDatePickerNewEvent(), startTime, getNeNotesTF(), endTime, getNeLocationInfoTF());
            model.addEvent(e);
            creationErrorLabel.setText("Event Created!");
            closeWindow();
        }else creationErrorLabel.setText("Creation failed. Please fill all fields marked with *.");


    }


    public void launchNewEventWindow(){
        model.loadTime();

        timeCBH.setItems(model.getHoursTime());
        timeCBM.setItems(model.getMinsTime());
        eTimeCBH.setItems(model.getHoursTime());
        eTimeCBM.setItems(model.getMinsTime());

        datePickerNewEvent.setValue(datePickerNewEvent.getCurrentDate());
        creationErrorLabel.setText("Required fields are marked with *.");
    }

    private Date getDatePickerNewEvent() {return Date.valueOf(datePickerNewEvent.getValue());}
    private int getTimeCBH() {return timeCBH.getSelectedItem();}
    private int getTimeCBM() {return timeCBM.getSelectedItem();}
    private int geteTimeCBH() {return eTimeCBH.getSelectedItem();}
    private int geteTimeCBM() {return eTimeCBM.getSelectedItem();}
    private String getNeNameTF() {return neNameTF.getText();}
    private String getNeLocationTF() {return neLocationTF.getText();}
    private String getNeNotesTF() {return neNotesTF.getText();}
    private String getNeLocationInfoTF() {return neLocationInfoTF.getText();}
}
