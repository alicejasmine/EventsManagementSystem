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
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class HomeViewController implements Initializable {


    @FXML private Label errorLabelHome;
    @FXML private Label up1Label;
    @FXML private Label up2Label;
    @FXML private Label up3Label;
    @FXML private Label up4Label;
    @FXML private Label added1Label;
    @FXML private Label added2Label;
    @FXML private Label added3Label;
    @FXML private Label added4Label;
    @FXML private ImageView up2ImageView;
    @FXML private ImageView up3ImageView;
    @FXML private ImageView up4ImageView;
    @FXML private ImageView add1ImageView;
    @FXML private ImageView add2ImageView;
    @FXML private ImageView add3ImageView;
    @FXML private ImageView add4ImageView;
    @FXML private ImageView up1ImageView;
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

    /**
     * This method is how we are making our buttons change dynamically based on what information is available.
     * Each button goes through a check, first does the relevant event exist? For example, are there any upcoming events? If so we gather up to 4. If not we disable buttons.
     * Next we check if the user input a filepath, if so, we attempt to fetch it.
     * We change the button to black and place the image with reduced opacity over it, and then place a white text label over that image.
     * If there is no image we keep our white buttons with black text.
     * If there should be an image but it is invalid, the button will be black with white text and the user will be warned.
     */
    private void eventButtonCreation(){
        if(model.getUpcomingEvents() != null){
            if(!model.getUpcomingEvents().get(0).getImageFilePath().equals(" ")) {
                try {
                    up1Button.setStyle("-fx-background-color: black");
                    Image previewImage = new Image(new FileInputStream(model.getUpcomingEvents().get(0).getImageFilePath()));
                    up1Label.setText(model.getUpcomingEvents().get(0).getName() + "\n\n" + model.getUpcomingEvents().get(0).getDate());
                    up1ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    System.out.println("file not found for upcoming event 1 image.");
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }
            }else{up1Button.setText(model.getUpcomingEvents().get(0).getName() + "\n\n" + model.getUpcomingEvents().get(0).getDate());}
        }else up1Button.setDisable(true);
        if(model.getUpcomingEvents().size() >= 2){
            if(!model.getUpcomingEvents().get(1).getImageFilePath().equals(" ")) {
                try {
                    up2Button.setStyle("-fx-background-color: black");
                    up2Label.setText(model.getUpcomingEvents().get(1).getName() + "\n\n" + model.getUpcomingEvents().get(1).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getUpcomingEvents().get(1).getImageFilePath()));
                    up2ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }

            } else{up2Button.setText(model.getUpcomingEvents().get(1).getName() + "\n\n" + model.getUpcomingEvents().get(1).getDate());}
        }else up2Button.setDisable(true);
        if(model.getUpcomingEvents().size() >= 3){
            if(!model.getUpcomingEvents().get(2).getImageFilePath().equals(" ")) {
                try {
                    up3Button.setStyle("-fx-background-color: black");
                    up3Label.setText(model.getUpcomingEvents().get(2).getName() + "\n\n" + model.getUpcomingEvents().get(2).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getUpcomingEvents().get(2).getImageFilePath()));
                    up3ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }
            } else {up3Button.setText(model.getUpcomingEvents().get(2).getName() + "\n\n" + model.getUpcomingEvents().get(2).getDate());}
        }else up3Button.setDisable(true);
        if(model.getUpcomingEvents().size() >= 4) {
            if(!model.getUpcomingEvents().get(3).getImageFilePath().equals(" ")) {
                try {
                    up4Button.setStyle("-fx-background-color: black");
                    up4Label.setText(model.getUpcomingEvents().get(3).getName() + "\n\n" + model.getUpcomingEvents().get(3).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getUpcomingEvents().get(3).getImageFilePath()));
                    up4ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }

            } else {up4Button.setText(model.getUpcomingEvents().get(3).getName() + "\n\n" + model.getUpcomingEvents().get(3).getDate());}
        }else up4Button.setDisable(true);


        if(model.getRecentAddedEvents() != null){
            if(!model.getRecentAddedEvents().get(0).getImageFilePath().equals(" ")) {
                try {
                    add1Button.setStyle("-fx-background-color: black");
                    added1Label.setText(model.getRecentAddedEvents().get(0).getName() + "\n\n" + model.getRecentAddedEvents().get(0).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getRecentAddedEvents().get(0).getImageFilePath()));
                    add1ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }
            } else{add1Button.setText(model.getRecentAddedEvents().get(0).getName() + "\n\n" + model.getRecentAddedEvents().get(0).getDate());}
        } else add1Button.setDisable(true);
        if(model.getRecentAddedEvents().size() >= 2){
            if(!model.getRecentAddedEvents().get(1).getImageFilePath().equals(" ")) {
                try {
                    add2Button.setStyle("-fx-background-color: black");
                    added2Label.setText(model.getRecentAddedEvents().get(1).getName() + "\n\n" + model.getRecentAddedEvents().get(1).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getRecentAddedEvents().get(1).getImageFilePath()));
                    add2ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }
            }else{add2Button.setText(model.getRecentAddedEvents().get(1).getName() + "\n\n" + model.getRecentAddedEvents().get(1).getDate());}
        } else add2Button.setDisable(true);
        if(model.getRecentAddedEvents().size() >= 3){
            if(!model.getRecentAddedEvents().get(2).getImageFilePath().equals(" ")) {
                try {
                    add3Button.setStyle("-fx-background-color: black");
                    added3Label.setText(model.getRecentAddedEvents().get(2).getName() + "\n\n" + model.getRecentAddedEvents().get(2).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getRecentAddedEvents().get(2).getImageFilePath()));
                    add3ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }
            }else{add3Button.setText(model.getRecentAddedEvents().get(2).getName() + "\n\n" + model.getRecentAddedEvents().get(2).getDate());}
        } else add3Button.setDisable(true);
        if(model.getRecentAddedEvents().size() >= 4){
            if(!model.getRecentAddedEvents().get(3).getImageFilePath().equals(" ")) {
                try {
                    add4Button.setStyle("-fx-background-color: black");
                    added4Label.setText(model.getRecentAddedEvents().get(3).getName() + "\n\n" + model.getRecentAddedEvents().get(3).getDate());
                    Image previewImage = new Image(new FileInputStream(model.getRecentAddedEvents().get(3).getImageFilePath()));
                    add4ImageView.setImage(previewImage);
                } catch (FileNotFoundException e) {
                    errorLabelHome.setText("An event indicates it should have an image but either the path is invalid or the file has been deleted.");
                }
            } else{add4Button.setText(model.getRecentAddedEvents().get(3).getName() + "\n\n" + model.getRecentAddedEvents().get(3).getDate());}
        } else add4Button.setDisable(true);
    }


    /**
     * These 8 action events are what happens when the user clicks one of our home event buttons.
     */
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
    @FXML private void logout(ActionEvent actionEvent) throws IOException {

        Parent root=FXMLLoader.load(getClass().getResource("/gui/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Event Manager");
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
