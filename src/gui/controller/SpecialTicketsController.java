package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SpecialTicketsController {
    public void NewSpecialTicket(ActionEvent actionEvent) {
    }

    public void deleteSpecialTicket(ActionEvent actionEvent) {
    }


    public void openTicketPreview(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketPreview.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Ticket Preview");
        stage.setScene(scene);
        stage.show();
    }
}
