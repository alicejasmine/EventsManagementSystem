package gui.controller;

import be.*;
import be.Event;
import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class EventsViewController implements Initializable {


    @FXML
    private Label
            eventNotesLabel,
            eventEndTimeLabel,
            locationGuidanceLabel,
            errorInfoLabel,
            userNameLabel,
            userAccessLabel;

    @FXML
    private Button
            newEventButton,
            deleteEventButton,
            editEventButton,
            homeButton,
            ticketInfo,
            logOutButton;

    @FXML
    private TableView<Event> eventTV;

    // Event Columns
    @FXML
    private TableColumn<Event, String> columnEventNameTV;
    @FXML
    private TableColumn<Event, String> columnEventLocalTV;
    @FXML
    private TableColumn<Event, Date> columnEventDateTV;
    @FXML
    private TableColumn<Event, Time> columnEventTimeTV;

    private Model model = Model.getModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventTV.setItems(model.getObsEvents());
        model.loadEventList();

        columnEventNameTV.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEventLocalTV.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnEventDateTV.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnEventTimeTV.setCellValueFactory(new PropertyValueFactory<>("time"));

        eventTV.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, selectedUser) -> {
            deleteEventButton.setDisable(selectedUser == null);
            model.setSelectedEvent(selectedUser);
        }));

        eventInfoView();
    }


    private void deleteListener() {

    }

    private void eventInfoView() {
        eventTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedUser) -> {
                    if (selectedUser != null) {
                        eventNotesLabel.setText(selectedUser.getNotes());
                        eventEndTimeLabel.setText(selectedUser.getEndTime().toString());
                        locationGuidanceLabel.setText(selectedUser.getLocationGuidance());
                    }
                });
    }

    private void deleteAlert() {
        Event selectedEvent = null;
        if (eventTV.getSelectionModel().getSelectedItem() != null) {
            selectedEvent = eventTV.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Event deletion confirmation");
            alert.setHeaderText("Do you really want to DELETE " + selectedEvent.getName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                errorInfoLabel.setText("Event " + selectedEvent.getName() + " has been deleted.");
                model.deleteEvent(selectedEvent);
            } else {
                alert.close();
            }
        } else errorInfoLabel.setText("Please select an event to be deleted.");
    }

    @FXML
    private void newEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/NewEventView.fxml"));
        Parent root = loader.load();
        NewEventViewController controller = loader.getController();
        controller.launchNewEventWindow();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Add new Event");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void deleteEvent(ActionEvent actionEvent) {
        deleteAlert();
    }


    @FXML
    private void editEvent(ActionEvent actionEvent) throws IOException {
        Event editEvent = null;
        if (eventTV.getSelectionModel().getSelectedItem() != null) {
            editEvent = eventTV.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/EditEventView.fxml"));
            Parent root = loader.load();
            EditEventController controller = loader.getController();
            controller.setEvent(editEvent);
            controller.setEditEventValues(editEvent);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Edit Event");
            stage.setScene(scene);
            stage.show();
        } else errorInfoLabel.setText("Please select an event to be edited.");
    }

    @FXML
    private void ticketInfo(ActionEvent actionEvent) throws IOException {
        Event selectedEvent = eventTV.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketView.fxml"));
            Parent root = loader.load();
            TicketViewController controller = loader.getController();
            controller.ticketViewLaunch();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Event and Ticket Information");
            stage.setScene(scene);
            stage.show();
        } else { errorInfoLabel.setText("Please select an event to see related tickets.");
        }

    }

    @FXML
    private void home(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/HomeView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logOutUser(ActionEvent actionEvent) {
    }
}