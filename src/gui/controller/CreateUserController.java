package gui.controller;

import be.*;
import be.Event;
import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;
import javafx.stage.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class CreateUserController implements Initializable{

    @FXML private Button adminButton;
    @FXML private Label errorLabel;
    @FXML private ImageView logoHome;
    @FXML private TextField userNameTF;
    @FXML private TextField passTF;
    @FXML private TextField firstNameTF;
    @FXML private TextField lastNameTF;
    @FXML private Button deleteUserButton;
    @FXML private Button editUserButton;
    @FXML private Label usernameLabel;
    @FXML private Button coordinatorButton;
    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<User, String> userNameColumn;
    @FXML private TableColumn<User, String> firstNameColumn;
    @FXML private TableColumn<User, String> lastNameColumn;

    private Model model = Model.getModel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image logoImage = new Image(new FileInputStream("resources/images/logoEASV.png"));
            logoHome.setImage(logoImage);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        userTableView.setItems(model.getObsUser());
        model.loadUserList();

        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
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

    public void setUsernameLabel() {
        usernameLabel.setText(model.getCurrentUser().getFirstName() + " " + model.getCurrentUser().getLastName());
        if(model.getCurrentUser().isAdmin()){
            coordinatorButton.setVisible(true);
        }
    }

    @FXML private void createUser(ActionEvent actionEvent) {
        if(!getUserNameTF().getText().isEmpty() && !getFirstNameTF().getText().isEmpty() && !getLastNameTF().getText().isEmpty() && !getPassTF().getText().isEmpty()){
            User user = new User(getUserNameTF().getText(), getPassTF().getText(), getFirstNameTF().getText(), getLastNameTF().getText());
            model.createUser(user);
            errorLabel.setText("");
        }else errorLabel.setText("One or more fields are not completed. No user has been created.");
    }

    @FXML private void deleteUser(ActionEvent actionEvent) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if(selectedUser != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("Do you really want to remove user " + selectedUser.getUserName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { //... user chose OK
                model.deleteUser(selectedUser);
                model.loadUserList();
                alert.close();
            } else {
                alert.close();
            }

            errorLabel.setText("");
        }else errorLabel.setText("Please select an account to be deleted.");
    }

    @FXML private void editUser(ActionEvent actionEvent) {
        if(userTableView.getSelectionModel().getSelectedItem() != null){
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();

            if(!getUserNameTF().getText().isEmpty()){
                selectedUser.setUserName(getUserNameTF().getText());
            }
            if(!getFirstNameTF().getText().isEmpty()){
                selectedUser.setFirstName(getFirstNameTF().getText());
            }
            if(!getLastNameTF().getText().isEmpty()){
                selectedUser.setLastName(getLastNameTF().getText());
            }
            if(!getPassTF().getText().isEmpty()){
                selectedUser.setUserPass(getPassTF().getText());
            }

            if(!getUserNameTF().getText().isEmpty() && !getFirstNameTF().getText().isEmpty() && !getLastNameTF().getText().isEmpty() && !getPassTF().getText().isEmpty()){
                User user = new User(getUserNameTF().getText(), getPassTF().getText(), getFirstNameTF().getText(), getLastNameTF().getText());
                model.createUser(user);
            }

            model.updateUser(selectedUser);
            errorLabel.setText("");
        }else errorLabel.setText("Please select an account to be edited.");
    }


    public TextField getUserNameTF() {
        return userNameTF;
    }

    public void setUserNameTF(TextField userNameTF) {
        this.userNameTF = userNameTF;
    }

    public TextField getPassTF() {
        return passTF;
    }

    public void setPassTF(TextField passTF) {
        this.passTF = passTF;
    }

    public TextField getFirstNameTF() {
        return firstNameTF;
    }

    public void setFirstNameTF(TextField firstNameTF) {
        this.firstNameTF = firstNameTF;
    }

    public TextField getLastNameTF() {
        return lastNameTF;
    }

    public void setLastNameTF(TextField lastNameTF) {
        this.lastNameTF = lastNameTF;
    }

    @FXML private void makeAdmin(ActionEvent actionEvent) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if(selectedUser != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Allow administrator privileges.");
            alert.setHeaderText("Do you really want to promote user " + selectedUser.getUserName() + " to admin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { //... user chose OK
                Admin admin = new Admin(selectedUser.getUserID());

                model.createAdmin(admin);
                model.loadUserList();
                alert.close();
            } else {
                alert.close();
            }

            errorLabel.setText("");
        }else errorLabel.setText("Please select an account to be promoted.");

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
