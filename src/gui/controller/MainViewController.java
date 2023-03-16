package gui.controller;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class MainViewController {

    @FXML
    private Label
            eventNameLabel,
            eventLocalLabel,
            eventDateLabel,
            eventTimeLabel,
            eventNotesLabel,
            eventEndTimeLabel,
            locationGuidanceLabel,
            userNameLabel,
            userAccessLabel;

    @FXML
    private Button
            newEventButton,
            deleteEventButton,
            editEventButton,
            logOutButton;


    @FXML
    private TableView eventTV;
    @FXML
    private TableColumn
            eventIDTV,
            eventNameTV,
            eventLocalTV,
            eventDateTV,
            eventTimeTV;


    public void newEvent(ActionEvent actionEvent) {
    }

    public void deleteEvent(ActionEvent actionEvent) {
    }

    public void editEvent(ActionEvent actionEvent) {
    }

    public void logOutUser(ActionEvent actionEvent) {
    }
}
