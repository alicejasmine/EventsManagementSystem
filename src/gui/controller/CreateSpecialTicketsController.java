package gui.controller;

import be.Event;
import be.TicketType;
import gui.model.Model;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateSpecialTicketsController implements Initializable {


    @FXML
    private Button coordinatorButton;
    @FXML
    private ImageView logo;
    @FXML
    private TextField ticketTypeTextfield, maxQuantityTextfield1, maxQuantityTextfield2;
    ;
    @FXML
    private MFXComboBox ticketTypeComboBox1, eventComboBox, ticketTypeComboBox2;
    @FXML
    private Label usernameLabel, errorInfoLabel1, addTypeErrorLabel, errorInfoLabel2;

    private Model model = Model.getModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.loadTicketTypeList();
        model.loadEventList();
        ticketTypeComboBox1.setItems(model.getTicketTypes());
        eventComboBox.setItems(model.getObsEvents());

        ticketTypeComboBox2.setItems(model.getTicketTypes());

        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logo.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void home(ActionEvent actionEvent) throws IOException {
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

    @FXML
    private void manageAllEvents(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/AllEvents.fxml"));
        Parent root = loader.load();
        EventsViewController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Event and Ticket Information");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Event Manager");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void addTicketType(ActionEvent actionEvent) {
        String ticketType = ticketTypeTextfield.getText();

        if (ticketType.isEmpty()) {
            addTypeErrorLabel.setText("Please write a Ticket Type to add.");
        } else if (ticketTypeComboBox1.getItems().toString().contains(ticketType)) {
            addTypeErrorLabel.setText("Ticket Type already exists.");
        } else {
            model.addTicketType(ticketType);
            addTypeErrorLabel.setText("Ticket Type " + ticketTypeTextfield.getText() + " added.");
            ticketTypeTextfield.clear();
        }

    }


    @FXML
    private void NewSpecialTicket(ActionEvent actionEvent) {
        TicketType selectedTicketType = (TicketType) ticketTypeComboBox1.getSelectionModel().getSelectedItem();
        Event selectedEvent = (Event) eventComboBox.getSelectionModel().getSelectedItem();

        if (selectedTicketType != null && selectedEvent != null && !maxQuantityTextfield1.getText().isEmpty()) {
            int maxQuantity = Integer.parseInt(maxQuantityTextfield1.getText());
            model.createSpecialTicket(selectedTicketType, selectedEvent, maxQuantity);
            errorInfoLabel1.setText(maxQuantityTextfield1.getText() + " ticket(s) of type " + selectedTicketType + " for the event " + selectedEvent + " created");
        } else {
            errorInfoLabel1.setText("Please provide ticket type, event and ticket quantity.");
        }
        maxQuantityTextfield1.clear();
        ticketTypeComboBox1.clear();
        eventComboBox.clear();
    }


    @FXML
    private void NewSpecialTicketWithoutEvent(ActionEvent actionEvent) {
        TicketType selectedTicketType = (TicketType) ticketTypeComboBox2.getSelectionModel().getSelectedItem();

        if (selectedTicketType != null && !maxQuantityTextfield2.getText().isEmpty()) {
            int maxQuantity = Integer.parseInt(maxQuantityTextfield2.getText());
            model.createSpecialTicketWithoutEvent(selectedTicketType, maxQuantity);
            errorInfoLabel2.setText(maxQuantityTextfield2.getText() + " ticket(s) of type " + selectedTicketType + " created");
        } else {
            errorInfoLabel2.setText("Please provide ticket type, event and ticket quantity.");
        }
        maxQuantityTextfield2.clear();
        ticketTypeComboBox2.clear();

    }


    @FXML
    private void openSpecialTickets(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SpecialTickets.fxml"));
        Parent root = loader.load();
        SpecialTicketsController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Special Tickets");
        stage.setScene(scene);
        stage.show();
    }

    public void setUsernameLabel() {
        usernameLabel.setText(model.getCurrentUser().getFirstName() + " " + model.getCurrentUser().getLastName());
        if (model.getCurrentUser().isAdmin()) {
            coordinatorButton.setVisible(true);
        }
    }

    @FXML
    private void manageCoordinators(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/CreateUser.fxml"));
        Parent root = loader.load();
        CreateUserController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Special Tickets");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newEventWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/NewEventView.fxml"));
            Parent root = loader.load();
            NewEventViewController controller = loader.getController();
            controller.launchNewEventWindow();
            controller.setUsernameLabel();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Add new Event");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error launching new event window.");
        }
    }

    /**
     * Method to open Special Tickets window in the same window
     */
    @FXML
    private void specialTicketsOverview(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SpecialTicketsOverview.fxml"));
        Parent root = loader.load();
        SpecialTicketsOverviewController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Special Tickets Overview");
        stage.setScene(scene);
        stage.show();
    }
}




