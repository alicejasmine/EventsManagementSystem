package gui.controller;

import be.*;
import gui.model.*;
import javafx.beans.property.SimpleStringProperty;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SpecialTicketsController implements Initializable {


    @FXML
    private Label usernameLabel, locationLabel, endTimeLabel, startTimeLabel, dateLabel, notesLabel, locationGuidanceLabel, errorInfoLabel;
    @FXML
    private Button coordinatorButton;
    @FXML
    private TableView<SpecialTicketsWrapper> specialTicketsTV;


    @FXML
    private TableColumn<SpecialTicketsWrapper, String> ColumnTicketTypeTV;

    @FXML
    private TableColumn<SpecialTicketsWrapper, String> ColumnEventNameTV;

    @FXML
    private TableColumn<SpecialTicketsWrapper, String> ColumnTicketIDTV;
    @FXML
    private ImageView logo;

    private Model model = Model.getModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        specialTicketsTV.setItems(model.getObsSpecialTickets());
        model.loadSpecialTicketList();

        ColumnTicketTypeTV.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        ColumnEventNameTV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvent().getName()));
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

        specialTicketsTV.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldUser, selectedUser) -> {
            if (selectedUser != null) {
                Event selectedEvent = selectedUser.getEvent();
                
                    dateLabel.setText(selectedEvent.getDate().toString());
                    locationLabel.setText(selectedEvent.getLocation());
                    notesLabel.setText(selectedEvent.getNotes());
                    startTimeLabel.setText(selectedEvent.getTime().toString());
                    if (selectedEvent.getEndTime() != null) {
                        endTimeLabel.setText(selectedEvent.getEndTime().toString());
                    }
                    if (selectedEvent.getLocationGuidance() != null) {
                        locationGuidanceLabel.setText(selectedEvent.getLocationGuidance());
                    }
                }

        }));


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
        model.deleteSpecialTicket(selectedTicket.getSpecialTicket());
    }


    @FXML
    private void openTicketPreview(ActionEvent actionEvent) throws IOException {
        SpecialTicketsWrapper selectedRow = specialTicketsTV.getSelectionModel().getSelectedItem();


        if (selectedRow != null) {
            Event selectedEvent = selectedRow.getEvent();
            SpecialTicket selectedSpecialTicket = selectedRow.getSpecialTicket();
            TicketType selectedTicketType = selectedRow.getTicketType();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketPreview.fxml"));
            Parent root = loader.load();

            TicketPreviewController controller = loader.getController();
            controller.setEvent(selectedEvent);
            controller.setSpecialTicket(selectedSpecialTicket);
            controller.setTicketType(selectedTicketType);
            controller.renderSpecialTicket();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Ticket Preview");
            stage.setScene(scene);
            stage.show();

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

    public void saveSpecialTicket(ActionEvent actionEvent) {
        SpecialTicketsWrapper selectedRow = specialTicketsTV.getSelectionModel().getSelectedItem();
        SpecialTicket selectedSpecialTicket = selectedRow.getSpecialTicket();
        TicketType selectedTicketType = selectedRow.getTicketType();
        Event selectedEvent = selectedRow.getEvent();

        if ((selectedEvent == null) || (selectedSpecialTicket == null)) {
            errorInfoLabel.setText("Please select a ticket to save");
        } else {
            String s = selectedSpecialTicket.getSpecialTicketID();
            String filename = "SpecialTicket-" + selectedEvent.getName() + "-" + s.substring(s.length() - 4) + ".pdf";
            File file = new File("resources/SpecialTickets/" + filename);
            if (file.exists()) {
                errorInfoLabel.setText("Error: File already saved");
            } else {
                model.saveSpecialTicket(selectedEvent, selectedSpecialTicket,selectedTicketType);
                errorInfoLabel.setText("File saved");
            }

        }
    }


    public void printSpecialTicket(ActionEvent actionEvent) {
        SpecialTicketsWrapper selectedRow = specialTicketsTV.getSelectionModel().getSelectedItem();
        SpecialTicket selectedSpecialTicket = selectedRow.getSpecialTicket();
        TicketType selectedTicketType = selectedRow.getTicketType();
        Event selectedEvent = selectedRow.getEvent();

        if ((selectedEvent != null) && (selectedSpecialTicket != null)) {
            model.printSpecialTicket(selectedEvent, selectedSpecialTicket, selectedTicketType);
        } else {
            errorInfoLabel.setText("Please select a ticket to print");
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

    /**
     * Method to open Special Tickets window in the same window
     */
    @FXML private void specialTicketsOverview(ActionEvent actionEvent) throws IOException {
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
