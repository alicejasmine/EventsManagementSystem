package gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddTicketTypeController {
    public Button ttCancelButton;

    public void closeAddTicketType(ActionEvent actionEvent) {
        Stage stage = (Stage) ttCancelButton.getScene().getWindow();
        stage.close();
    }
}
