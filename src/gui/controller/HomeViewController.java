package gui.controller;

import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
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
import java.net.*;
import java.util.*;

public class HomeViewController implements Initializable {

    @FXML private MFXButton up1Button;
    @FXML private MFXButton up2Button;
    @FXML private MFXButton up3Button;
    @FXML private MFXButton up4Button;
    @FXML private MFXButton add1Button;
    @FXML private MFXButton add2Button;
    @FXML private MFXButton add3Button;
    @FXML private MFXButton add4Button;
    @FXML private Label usernameLabel;
    @FXML private Button coordinatorButton;
    @FXML
    private ImageView logoHome;




    private Model model = Model.getModel();


    @FXML private void manageAllEvents(ActionEvent actionEvent) throws IOException {

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.loadEventList();
        model.loadTime();
        model.loadTicketList();
        model.loadEventTicketList();
        model.loadUpcoming();
        model.loadRecentlyAdded();

        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logoHome.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        eventButtonCreation();
    }

    private void eventButtonCreation(){
        if(model.getUpcomingEvents() != null){
            up1Button.setText(model.getUpcomingEvents().get(0).getName() + "\n\n" + model.getUpcomingEvents().get(0).getDate());
        }else up1Button.setDisable(true);
        if(model.getUpcomingEvents().size() >= 2){
            up2Button.setText(model.getUpcomingEvents().get(1).getName() + "\n\n" + model.getUpcomingEvents().get(1).getDate());
        }else up2Button.setDisable(true);
        if(model.getUpcomingEvents().size() >= 3){
            up3Button.setText(model.getUpcomingEvents().get(2).getName() + "\n\n" + model.getUpcomingEvents().get(2).getDate());
        }else up3Button.setDisable(true);
        if(model.getUpcomingEvents().size() >= 4) {
            up4Button.setText(model.getUpcomingEvents().get(3).getName() + "\n\n" + model.getUpcomingEvents().get(3).getDate());
        }else up4Button.setDisable(true);


        if(model.getRecentAddedEvents() != null){
            add1Button.setText(model.getRecentAddedEvents().get(0).getName() + "\n\n" + model.getRecentAddedEvents().get(0).getDate());
        } else add1Button.setDisable(true);
        if(model.getRecentAddedEvents().size() >= 2){
            add2Button.setText(model.getRecentAddedEvents().get(1).getName() + "\n\n" + model.getRecentAddedEvents().get(1).getDate());
        } else add2Button.setDisable(true);
        if(model.getRecentAddedEvents().size() >= 3){
            add3Button.setText(model.getRecentAddedEvents().get(2).getName() + "\n\n" + model.getRecentAddedEvents().get(2).getDate());
        } else add3Button.setDisable(true);
        if(model.getRecentAddedEvents().size() >= 4){
            add4Button.setText(model.getRecentAddedEvents().get(3).getName() + "\n\n" + model.getRecentAddedEvents().get(3).getDate());
        } else add4Button.setDisable(true);
    }

    private void openEventWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketView.fxml"));
            Parent root = loader.load();
            TicketViewController controller = loader.getController();
            controller.ticketViewLaunch();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Event and Ticket Information");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            System.out.println("No selected event to open event ticket window.");
        }
    }

    @FXML private void homeEventSelectionUpcoming1(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getUpcomingEvents().get(0));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionUpcoming2(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getUpcomingEvents().get(1));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionUpcoming3(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getUpcomingEvents().get(2));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionUpcoming4(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getUpcomingEvents().get(3));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionAdd1(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getRecentAddedEvents().get(0));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionAdd2(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getRecentAddedEvents().get(1));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionAdd3(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getRecentAddedEvents().get(2));
        openEventWindow(actionEvent);
    }

    @FXML private void homeEventSelectionAdd4(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getRecentAddedEvents().get(3));
        openEventWindow(actionEvent);
    }

    public void setUsernameLabel() {
        usernameLabel.setText(model.getCurrentUser().getFirstName() + " " + model.getCurrentUser().getLastName());
        if(model.getCurrentUser().isAdmin()){
            coordinatorButton.setVisible(true);
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

    @FXML private void logout(ActionEvent actionEvent) throws IOException {

        Parent root=FXMLLoader.load(getClass().getResource("/gui/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Event Manager");
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void newUser(ActionEvent actionEvent) throws IOException {
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
}
