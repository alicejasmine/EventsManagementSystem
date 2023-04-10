package gui.controller;

import be.Event;
import be.SpecialTicketsWrapper;
import gui.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SpecialTicketsController implements Initializable {


    @FXML
    private Label usernameLabel, locationLabel, endTimeLabel, startTimeLabel, dateLabel, notesLabel, locationGuidanceLabel, errorInfoLabel;
    @FXML
    private Button coordinatorButton;
    @FXML
    private TableView specialTicketsTV;

    @FXML
    private TableColumn ColumnTicketTypeTV, ColumnEventNameTV, ColumnTicketIDTV;
    @FXML
    private ImageView logo;

    private Model model = Model.getModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        specialTicketsTV.setItems(model.getObsSpecialTickets());
        model.loadSpecialTicketList();


        ColumnTicketTypeTV.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        ColumnEventNameTV.setCellValueFactory(new PropertyValueFactory<>("event"));
        ColumnTicketIDTV.setCellValueFactory(new PropertyValueFactory<>("specialTicket"));
        setLabels();

        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logo.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabels() {
        /*
        SpecialTicketsWrapper selectedRow = (SpecialTicketsWrapper) specialTicketsTV.getSelectionModel().getSelectedItem();
        System.out.println(selectedRow);
        if (selectedRow != null) {
            Event selectedEvent = selectedRow.getEvent();
            //System.out.println(selectedEvent);
        dateLabel.setText(selectedEvent.getDate().toString());
        locationLabel.setText(selectedEvent.getLocation());
        locationGuidanceLabel.setText(selectedEvent.getLocationGuidance());
        notesLabel.setText(selectedEvent.getNotes());
        startTimeLabel.setText(selectedEvent.getTime().toString());
        endTimeLabel.setText(selectedEvent.getName());
       
         */
    }

    public void setUsernameLabel() {
        usernameLabel.setText(model.getCurrentUser().getFirstName() + " " + model.getCurrentUser().getLastName());
        if (model.getCurrentUser().isAdmin()) {
            coordinatorButton.setVisible(true);
        }
    }


    @FXML
    private void deleteSpecialTicket(ActionEvent actionEvent) {
        SpecialTicketsWrapper selectedTicket = (SpecialTicketsWrapper) specialTicketsTV.getSelectionModel().getSelectedItem();
        System.out.println(selectedTicket);
        model.deleteSpecialTicket(selectedTicket.getSpecialTicket());
    }


    @FXML
    private void openTicketPreview(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketPreview.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Ticket Preview");
        stage.setScene(scene);
        stage.show();

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
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void openCreateSpecialTicket(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/CreateSpecialTicket.fxml"));
        Parent root = loader.load();
        CreateSpecialTicketsController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Create Special Ticket");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openSpecialTicketsOverview(ActionEvent actionEvent) throws IOException {
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
}
