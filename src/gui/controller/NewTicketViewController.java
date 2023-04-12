package gui.controller;

import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTicketViewController {
    @FXML
    private TextField customerNameTF;
    @FXML
    private TextField customerEmailTF;
    @FXML
    private Label creationErrorLabel;
    @FXML
    private Button ticketCreateButton;
    @FXML
    private Button cancelButton;

    private int maxName = 50;

    private int maxEmail = 25;

    private Model model = Model.getModel();



    private void createNewTicket(){
        if(!customerNameTF.getText().isEmpty()&&!customerEmailTF.getText().isEmpty()){
            String customerName = customerNameTF.getText();
            String customerEmail= customerEmailTF.getText();
            int eventID = model.getSelectedEvent().getId();
            //System.out.println(customerName + customerEmail + eventID);
            if(!validateNameTextFieldLength() && !validateEmail()) {
                alertEmailAndNameTextField();
            } else if (!validateEmail()) {
                alertEmailValidationTextField();
            } else if (!validateNameTextFieldLength()) {
                alertNameTextField();
            }
            else {
                model.addTicket(customerName, customerEmail, eventID);
            }
            model.loadEventTicketList();
        }
        else{creationErrorLabel.setText("Insert name and email");}
    }

    private void alertNameTextField() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Name");
        alert.setContentText("Name is too long, max is 50 characters.");
        alert.showAndWait();
    }

    private void alertEmailAndNameTextField(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Name and Email");
        alert.setContentText("Name is too long, max is 50 characters. Please enter valid email.");
        alert.showAndWait();
    }

    private void alertEmailValidationTextField() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Email");
        alert.setContentText("Please enter valid email");
        alert.showAndWait();
    }
    private boolean validateNameTextFieldLength() {
        if(customerNameTF.getText().length() > maxName) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");
        Matcher m = p.matcher(customerEmailTF.getText());

        if(m.find() && m.group().equals(customerEmailTF.getText()) && !(customerEmailTF.getText().length() > maxEmail)) {
            return true;
        } else {
            return false;
        }
    }

    private void closeWindow(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void createTicket(ActionEvent actionEvent) {
        createNewTicket();
    }
    @FXML
    private void cancel(ActionEvent actionEvent) {
        closeWindow();
    }

}
