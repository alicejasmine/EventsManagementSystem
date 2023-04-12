package gui.controller;

import be.*;
import be.Event;
import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

public class NewEventViewController implements Initializable{
    @FXML
    private Label usernameLabel;
    @FXML
    private Button coordinatorButton;
    @FXML
    private ImageView logoHome;
    @FXML
    private MFXDatePicker datePickerNewEvent;
    @FXML
    private MFXComboBox<Integer> timeCBH, timeCBM, eTimeCBH, eTimeCBM;
    @FXML
    private Label creationErrorLabel;
    @FXML
    private TextField neNameTF, neLocationTF;
    @FXML
    private TextArea neNotesTF, neLocationInfoTF;
    @FXML
    private Button neCreateButton;


    private Model model = Model.getModel();


    @FXML
    private void createEvent(ActionEvent actionEvent) {
        createNewEvent();
    }


    private void createNewEvent(){

        Time startTime = null;
        Time endTime = new Time(0,0,0); // The end time is automatically set for 00:00:00 if there is no time selection

        if(eTimeCBM.getSelectedItem() != null && eTimeCBH.getSelectedItem() != null){
            endTime = new Time(geteTimeCBH(), geteTimeCBM(), 0);
        }

        if(timeCBH.getSelectedItem() != null && timeCBM.getSelectedItem() != null){
            startTime = new Time(getTimeCBH(), getTimeCBM(), 0);
        }

        Event e = null;

        if(!getNeNameTF().isEmpty()&& !getNeLocationTF().isEmpty() && !getNeNotesTF().isEmpty() && startTime != null) {
            e = new Event(getNeNameTF(), getNeLocationTF(), getDatePickerNewEvent(), startTime, getNeNotesTF(), endTime, getNeLocationInfoTF());
            model.addEvent(e);
            creationErrorLabel.setText("Event Created!");
        }else creationErrorLabel.setText("Creation failed. Please fill all fields marked with *.");


    }


    public void launchNewEventWindow(){
        model.loadTime();

        timeCBH.setItems(model.getHoursTime());
        timeCBM.setItems(model.getMinsTime());
        eTimeCBH.setItems(model.getHoursTime());
        eTimeCBM.setItems(model.getMinsTime());

        datePickerNewEvent.setValue(datePickerNewEvent.getCurrentDate());
        creationErrorLabel.setText("Required fields are marked with *.");
    }

    private Date getDatePickerNewEvent() {return Date.valueOf(datePickerNewEvent.getValue());}
    private int getTimeCBH() {return timeCBH.getSelectedItem();}
    private int getTimeCBM() {return timeCBM.getSelectedItem();}
    private int geteTimeCBH() {return eTimeCBH.getSelectedItem();}
    private int geteTimeCBM() {return eTimeCBM.getSelectedItem();}
    private String getNeNameTF() {return neNameTF.getText();}
    private String getNeLocationTF() {return neLocationTF.getText();}
    private String getNeNotesTF() {return neNotesTF.getText();}
    private String getNeLocationInfoTF() {return neLocationInfoTF.getText();}

    public void imageFileExplorer(ActionEvent actionEvent) {
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

    @FXML private void home(ActionEvent actionEvent) throws IOException {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logoHome.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
