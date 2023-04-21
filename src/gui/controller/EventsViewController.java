package gui.controller;

import be.Event;
import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class EventsViewController implements Initializable {


    @FXML private Label usernameLabel;
    @FXML private Button coordinatorButton;
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
    @FXML
    private ImageView logoHome;

    private Model model = Model.getModel();

    /**
     * We use initialize to set the tableview of events as well as a listener to change the selected event in the model.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logoHome.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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


    public void setUsernameLabel() {// set our username label to the users name
        usernameLabel.setText(model.getCurrentUser().getFirstName() + " " + model.getCurrentUser().getLastName());
        if(model.getCurrentUser().isAdmin()){
            coordinatorButton.setVisible(true);
        }
    }

    private void eventInfoView() {// sets the labels for an event when it is clicked on the tv.
        eventTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedUser) -> {
                    if (selectedUser != null) {
                        eventNotesLabel.setText(selectedUser.getNotes());
                        locationGuidanceLabel.setText(selectedUser.getLocationGuidance());
                        if(selectedUser.getEndTime() != null){
                        eventEndTimeLabel.setText(selectedUser.getEndTime().toString());
                        }else eventEndTimeLabel.setText("This party doesn't stop!");
                    }
                });
    }

    private void deleteAlert() {// This is an alert to both warn the user that they clicked delete, and of what event will be deleted.
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
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
        HomeViewController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }




    @FXML private void logout(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("/gui/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Event Manager");
        stage.setScene(scene);
        stage.show();
    }



    @FXML private void specialTicketsOverview(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SpecialTicketsOverview.fxml"));
        Parent root = loader.load();
        SpecialTicketsOverviewController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Special Tickets");
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void openCreateSpecialTicket(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/gui/view/CreateSpecialTicket.fxml"));
        Parent root=loader.load();
        CreateSpecialTicketsController controller = loader.getController();
        controller.setUsernameLabel();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Create Special Ticket");
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void openSpecialTickets(ActionEvent actionEvent) throws IOException {
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

    @FXML private void manageCoordinators(ActionEvent actionEvent) throws IOException {
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

    @FXML private void openTicketWindow(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount() == 2) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketView.fxml"));
            Parent root = loader.load();
            TicketViewController controller = loader.getController();
            controller.ticketViewLaunch();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Event and Ticket Information");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML private void newEventWindow(ActionEvent actionEvent) {
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
        }catch (IOException e){
            System.out.println("Error launching new event window.");
        }
    }
}
