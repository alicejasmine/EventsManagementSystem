package gui.controller;

import be.Event;
import be.TicketType;
import gui.model.Model;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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


    @FXML private Button coordinatorButton;
    @FXML
    private ImageView logo;
    private Model model = Model.getModel();

    @FXML
    private TextField ticketTypeTextfield, maxQuantityTextfield;

    @FXML
    private MFXComboBox ticketTypeComboBox, eventComboBox;

    @FXML
    private Label usernameLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.loadTicketTypeList();
        model.loadEventList();
        ticketTypeComboBox.setItems(model.getTicketType());
        eventComboBox.setItems(model.getObsEvents());

        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logo.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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

    public void logout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Event Manager");
        stage.setScene(scene);
        stage.show();
    }


    public void openSpecialTicketsOverview(ActionEvent actionEvent) throws IOException {
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



    public void addTicketType(ActionEvent actionEvent) {
        String list = ticketTypeComboBox.getItems().toString();
        if (!(list.contains(ticketTypeTextfield.getText())) && !ticketTypeTextfield.getText().isEmpty() && !maxQuantityTextfield.getText().isEmpty()) {
            int maxQuantity = Integer.parseInt(maxQuantityTextfield.getText());
            model.addTicketType(ticketTypeTextfield.getText(), maxQuantity);
        }
        ticketTypeTextfield.clear();
        maxQuantityTextfield.clear();
    }



    public void NewSpecialTicket(ActionEvent actionEvent) {
        TicketType selectedTicketType = (TicketType) ticketTypeComboBox.getSelectionModel().getSelectedItem();
        Event selectedEvent = (Event) eventComboBox.getSelectionModel().getSelectedItem();
        if (selectedTicketType != null)  { //&& selectedEvent != null)
            model.createSpecialTicket(selectedTicketType, selectedEvent);
        }
    }


    public void openSpecialTickets(ActionEvent actionEvent) throws IOException {
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
        if(model.getCurrentUser().isAdmin()){
            coordinatorButton.setVisible(true);
        }
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
}




