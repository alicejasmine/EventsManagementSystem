package gui.controller;


import be.*;
import be.Event;
import com.google.zxing.WriterException;
import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.*;

import java.awt.print.PrinterException;
import java.io.*;
import java.net.URL;

public class TicketViewController {

    @FXML
    private Button homeButton, newTicketButton;
    @FXML
    private Label
            eventLocationLabel,
            eventDateLabel,
            eventStartTimeLabel,
            eventNotesLabel,
            eventGuidanceLocationLabel,
            eventNameLabel,
            errorLabel;
    @FXML
    private TableView<Ticket> ticketsTV;
    @FXML
    private TableColumn<Ticket, String> columnCustomerName;
    @FXML
    private TableColumn<Ticket, String> columnCustomerEmail;
    @FXML
    private TableColumn<Ticket, String> columnTicketID;

    private Event selectedEvent;

    private Model model = Model.getModel();


    public void ticketViewLaunch() {
        selectedEvent = model.getSelectedEvent();
        model.loadTicketList();
        setTV();
        setLabels();
    }

    private void setTV() {
        ticketsTV.setItems(model.eventFilteredTickets());

        columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        columnCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        columnTicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
    }

    private void setLabels() {
        eventDateLabel.setText(selectedEvent.getDate().toString());
        eventLocationLabel.setText(selectedEvent.getLocation());
        eventGuidanceLocationLabel.setText(selectedEvent.getLocationGuidance());
        eventNotesLabel.setText(selectedEvent.getNotes());
        eventStartTimeLabel.setText(selectedEvent.getTime().toString());
        eventNameLabel.setText(selectedEvent.getName());
    }

    public void home(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/HomeView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    public void openNewTicketView(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/NewTicketView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Add new Event");
        stage.setScene(scene);
        stage.show();
    }

    public void saveTicket(ActionEvent actionEvent) {
        Ticket selectedTicket = ticketsTV.getSelectionModel().getSelectedItem();
        Event selectedEvent = model.getSelectedEvent();
        if ((selectedEvent == null) || (selectedTicket == null)){
            errorLabel.setText("Please select a ticket to save");
        }
        else{
        String s = selectedTicket.getTicketID();
        String filename = "Ticket-" + selectedEvent.getName() + "-" + s.substring(s.length() - 4) + ".pdf";
        File file = new File("resources/" + filename);
            if (file.exists()) {
                errorLabel.setText("Error: File already saved");
            } else {
                model.saveTicket(selectedEvent, selectedTicket);
                errorLabel.setText("File saved in resources folder");
            }

    }}

    public void printTicket(ActionEvent actionEvent) {
        Ticket selectedTicket = ticketsTV.getSelectionModel().getSelectedItem();
        Event selectedEvent = model.getSelectedEvent();

        if ((selectedEvent != null) && (selectedTicket != null)) {
            model.printTicket(selectedEvent, selectedTicket);
        } else {
            errorLabel.setText("Please select a ticket to print");
        }

    }
}