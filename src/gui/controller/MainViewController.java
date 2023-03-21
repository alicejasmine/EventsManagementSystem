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

public class MainViewController implements Initializable{


    @FXML
    private Tab ticketsTab;

    @FXML
    private TextField
            customerNameTF,
            customerEmailTF;

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
            userAccessLabel,
            errorInfoLabel,
            eventNameLabelTicketTab;

    @FXML
    private Button
            newEventButton,
            deleteEventButton,
            editEventButton,
            logOutButton,
            printtTicketButton,
            saveTicketButton,
            newTicketButton;

    // Table views
    @FXML
    private TableView<Event> eventTV;
    @FXML
    private TableView<Ticket> ticketTV;

    // Event Columns
    @FXML
    private TableColumn<Event, Integer> columnEventIDTV;
    @FXML
    private TableColumn<Event, String> columnEventNameTV;
    @FXML
    private TableColumn<Event, String> columnEventLocalTV;
    @FXML
    private TableColumn<Event, Date> columnEventDateTV;
    @FXML
    private TableColumn<Event, Time> columnEventTimeTV;

    // Ticket Columns
    @FXML
    private TableColumn<Event, Integer> eventIDTTV;
    @FXML
    private TableColumn<Ticket, String> customerNameTTV;
    @FXML
    private TableColumn<Ticket, String> customerEmailTTV;
    @FXML
    private TableColumn<Ticket, String> ticketUUIDTTV;



    private Model model = Model.getModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventTV.setItems(model.getObsEvents());
        ticketTV.setItems(model.getObsTickets());

        model.loadEventList();
        model.loadTicketList();

        columnEventIDTV.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnEventNameTV.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEventLocalTV.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnEventDateTV.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnEventTimeTV.setCellValueFactory(new PropertyValueFactory<>("time"));



        eventIDTTV.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        customerNameTTV.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerEmailTTV.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        ticketUUIDTTV.setCellValueFactory(new PropertyValueFactory<>("ticketID"));



        eventInfoView();
        deleteListener();
    }

    private void deleteListener(){
        eventTV.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, selectedUser) -> {
            deleteEventButton.setDisable(selectedUser == null);
            ticketsTab.setDisable(selectedUser == null);
        }));
    }

    private void eventInfoView(){
        eventTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedUser) ->{
                    if(selectedUser != null){
                        eventNameLabel.setText(selectedUser.getName());
                        eventLocalLabel.setText(selectedUser.getLocation());
                        eventDateLabel.setText(selectedUser.getDate().toString());
                        eventTimeLabel.setText(selectedUser.getTime().toString());
                        eventNotesLabel.setText(selectedUser.getNotes());
                        eventEndTimeLabel.setText(selectedUser.getEndTime().toString());
                        locationGuidanceLabel.setText(selectedUser.getLocationGuidance());
                    }
                } );
    }

    private void createNewTicket(){
        if(!customerNameTF.getText().isEmpty()&&!customerEmailTF.getText().isEmpty()){
            String customerName = customerNameTF.getText();
            String customerEmail= customerEmailTF.getText();
            int eventID = eventTV.getSelectionModel().getSelectedItem().getId();
            model.addTicket(customerName, customerEmail, eventID);
        }
    }
    private void deleteAlert(){
        Event selectedEvent = null;
        if(eventTV.getSelectionModel().getSelectedItem() != null){
            selectedEvent = eventTV.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Event deletion confirmation");
            alert.setHeaderText("Do you really want to DELETE " + selectedEvent.getName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                errorInfoLabel.setText("Event "+selectedEvent.getName()+" has been deleted.");
                model.deleteEvent(selectedEvent);
            } else {
                alert.close();
            }
        }else  errorInfoLabel.setText("Please select an event to be deleted.");
    }
    @FXML
    private void newEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/NewEventView.fxml"));
        Parent root = loader.load();
        NewEventViewController controller = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Add new Event");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void deleteEvent(ActionEvent actionEvent){
        deleteAlert();
    }


    @FXML
    private void editEvent(ActionEvent actionEvent) throws IOException{
        Event editEvent = null;
        if(eventTV.getSelectionModel().getSelectedItem() != null){
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
        }else errorInfoLabel.setText("Please select an event to be edited.");
    }

    public void logOutUser(ActionEvent actionEvent) {
    }


    public void newTicket(ActionEvent actionEvent) {
        createNewTicket();
    }

    public void printTicket(ActionEvent actionEvent) {
    }

    public void saveTicket(ActionEvent actionEvent) {
    }
}
