package gui.controller;


import be.*;
import be.Event;
import gui.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketViewController implements Initializable {

    @FXML
    private TextField textFieldSearchTickets;

    @FXML
    private ImageView backArrow;
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
            errorLabel,
            eventEndTimeLabel,
            AvailableSoldTickets;
    @FXML
    private TableView<Ticket> ticketsTV;
    @FXML
    private TableColumn<Ticket, String> columnCustomerName;
    @FXML
    private TableColumn<Ticket, String> columnCustomerEmail;
    @FXML
    private TableColumn<Ticket, String> columnTicketID;

    private Event selectedEvent;

    private Stage previousWindow;


    private Model model = Model.getModel();

    public void initialize(URL url, ResourceBundle rb) {
        textFieldSearchTickets.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                model.search(newValue, selectedEvent.getId());
            }
        });


    }


    public void ticketViewLaunch() {

        selectedEvent = model.getSelectedEvent();
        model.loadTicketList();
        setTV();
        setLabels();
        setBackArrow();
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
        eventEndTimeLabel.setText(selectedEvent.getName());
    }


    @FXML
    private void openNewTicketView(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/NewTicketView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Add new Event");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void saveTicket(ActionEvent actionEvent) {
        Ticket selectedTicket = ticketsTV.getSelectionModel().getSelectedItem();
        Event selectedEvent = model.getSelectedEvent();
        if ((selectedEvent == null) || (selectedTicket == null)) {
            errorLabel.setText("Please select a ticket to save");
        } else {
            String s = selectedTicket.getTicketID();
            String filename = "Ticket-" + selectedEvent.getName() + "-" + s.substring(s.length() - 4) + ".pdf";
            File file = new File("resources/" + filename);
            if (file.exists()) {
                errorLabel.setText("Error: File already saved");
            } else {
                model.saveTicket(selectedEvent, selectedTicket);
                errorLabel.setText("File saved in resources folder");
            }

        }
    }

    @FXML
    private void printTicket(ActionEvent actionEvent) {
        Ticket selectedTicket = ticketsTV.getSelectionModel().getSelectedItem();
        Event selectedEvent = model.getSelectedEvent();

        if ((selectedEvent != null) && (selectedTicket != null)) {
            model.printTicket(selectedEvent, selectedTicket);
        } else {
            errorLabel.setText("Please select a ticket to print");
        }

    }

    public void setBackArrow() {

        try {
            Image logoImage = new Image(new FileInputStream("resources/images/backArrow.png"));
            backArrow.setImage(logoImage);

            backArrow.setOnMouseClicked(event -> {
                if (previousWindow != null) {
                    previousWindow.show();
                }
                Stage currentStage = (Stage) backArrow.getScene().getWindow();
                currentStage.close();
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openTicketPreview(ActionEvent actionEvent) throws IOException {
        if (ticketsTV.getSelectionModel().getSelectedItem() != null) {
            Ticket selectedTicket = ticketsTV.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketPreview.fxml"));
            Parent root = loader.load();
            TicketPreviewController controller = loader.getController();
            controller.setEvent(selectedEvent);
            controller.setTicket(selectedTicket);
            controller.renderTicket();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Ticket Preview");
            stage.setScene(scene);
            stage.show();
        }
        else{errorLabel.setText("Please select a ticket to see its preview");}
    }

    @FXML
    private void returnHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/HomeView.fxml"));
        Parent root = loader.load();
        HomeViewController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }
}
