package gui.controller;

import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

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

    private Model model = Model.getModel();

    private void createNewTicket(){
        if(!customerNameTF.getText().isEmpty()&&!customerEmailTF.getText().isEmpty()){
            String customerName = customerNameTF.getText();
            String customerEmail= customerEmailTF.getText();
            int eventID = model.getSelectedEvent().getId();
            //System.out.println(customerName + customerEmail + eventID);
            model.addTicket(customerName, customerEmail, eventID);
            model.loadEventTicketList();
        }
        else{creationErrorLabel.setText("Insert name and email");}

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
