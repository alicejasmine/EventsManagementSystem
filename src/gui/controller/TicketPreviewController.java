package gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicketPreviewController {
    public void closeTicketPreview(ActionEvent actionEvent) {
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
