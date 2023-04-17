package gui.controller;

import be.*;
import gui.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SpecialTicketsWithoutEventController implements Initializable {

    @FXML
    private Button coordinatorButton;
    @FXML
    private ImageView logo;
    @FXML
    private TableView<SpecialTicketsWithoutEventWrapper> specialTicketsWithoutEventTV;
    @FXML
    private TableColumn<SpecialTicketsWithoutEventWrapper,String> ColumnTicketTypeTV;
    @FXML
    private TableColumn<SpecialTicketsWithoutEventWrapper,String> ColumnTicketIDTV;
    @FXML
    private Label errorLabel, usernameLabel;


    private Model model = Model.getModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        specialTicketsWithoutEventTV.setItems(model.getObsSpecialTicketsWithoutEvent());
        model.loadSpecialTicketWithoutEvent();

        ColumnTicketTypeTV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketType().getTicketTypeName()));
        ColumnTicketIDTV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialTicketWithoutEvent().getSpecialTicketID()));


        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logo.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void logout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }



    public void deleteSpecialTicketWithoutEvent(ActionEvent actionEvent) {
        SpecialTicketsWithoutEventWrapper selectedTicket = specialTicketsWithoutEventTV.getSelectionModel().getSelectedItem();
        model.deleteSpecialTicketWithoutEvent(selectedTicket.getSpecialTicketWithoutEvent());
    }
    public void openTicketPreview(ActionEvent actionEvent) throws IOException {
        SpecialTicketsWithoutEventWrapper selectedRow = specialTicketsWithoutEventTV.getSelectionModel().getSelectedItem();


        if (selectedRow != null) {
            SpecialTicketWithoutEvent selectedSpecialTicket = selectedRow.getSpecialTicketWithoutEvent();
            TicketType selectedTicketType = selectedRow.getTicketType();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketPreview.fxml"));
            Parent root = loader.load();

            TicketPreviewController controller = loader.getController();

            controller.setSelectedSpecialTicketWithoutEvent(selectedSpecialTicket);
            controller.setTicketType(selectedTicketType);
            controller.renderSpecialTicketWithoutEvent();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Ticket Preview");
            stage.setScene(scene);
            stage.show();

        }
    }

    public void saveSpecialTicket(ActionEvent actionEvent) {
        SpecialTicketsWithoutEventWrapper selectedRow = specialTicketsWithoutEventTV.getSelectionModel().getSelectedItem();
        SpecialTicketWithoutEvent selectedSpecialTicket = selectedRow.getSpecialTicketWithoutEvent();
        TicketType selectedTicketType = selectedRow.getTicketType();

        if (selectedSpecialTicket == null) {
            errorLabel.setText("Please select a ticket to save");
        } else {
            String s = selectedSpecialTicket.getSpecialTicketID();
            String filename = "SpecialTicket-" + s.substring(s.length() - 4) + ".pdf";
            File file = new File("resources/SpecialTickets/" + filename);
            if (file.exists()) {
                errorLabel.setText("Error: File already saved");
            } else {
                model.saveSpecialTicketWithoutEvent(selectedSpecialTicket,selectedTicketType);
                errorLabel.setText("File saved");
            }

        }
    }

    public void printSpecialTicket(ActionEvent actionEvent) {
        SpecialTicketsWithoutEventWrapper selectedRow = specialTicketsWithoutEventTV.getSelectionModel().getSelectedItem();
        SpecialTicketWithoutEvent selectedSpecialTicket = selectedRow.getSpecialTicketWithoutEvent();
        TicketType selectedTicketType = selectedRow.getTicketType();


        if (selectedSpecialTicket != null) {
            model.printSpecialTicketWithoutEvent(selectedSpecialTicket, selectedTicketType);
        } else {
            errorLabel.setText("Please select a ticket to print");
        }

    }

    public void home(ActionEvent actionEvent) throws IOException {
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

    public void manageAllEvents(ActionEvent actionEvent) throws IOException {
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

    public void newEventWindow(ActionEvent actionEvent) {
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

    public void specialTicketsOverview(ActionEvent actionEvent) throws IOException {
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

    public void openCreateSpecialTicket(ActionEvent actionEvent) throws IOException {
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

    public void manageCoordinators(ActionEvent actionEvent) throws IOException {
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

    public void switchToWithEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/SpecialTickets.fxml"));
        Parent root = loader.load();
        SpecialTicketsController controller=loader.getController();
        controller.setUsernameLabel();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setUsernameLabel() {
        usernameLabel.setText(model.getCurrentUser().getFirstName() + " " + model.getCurrentUser().getLastName());
        if (model.getCurrentUser().isAdmin()) {
            coordinatorButton.setVisible(true);
        }
    }

}
