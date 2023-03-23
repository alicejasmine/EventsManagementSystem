package gui.controller;

import gui.model.*;
import io.github.palexdev.materialfx.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class HomeViewController implements Initializable {

    public MFXButton up1Button;
    public MFXButton up2Button;
    public MFXButton up3Button;
    public MFXButton up4Button;
    public MFXButton add1Button;
    public MFXButton add2Button;
    public MFXButton add3Button;
    public MFXButton add4Button;
    @FXML
    private Label //upcoming labels
            upcoming1LabelName, upcoming1LabelDate,
            upcoming2LabelName, upcoming2LabelDate,
            upcoming3LabelName, upcoming3LabelDate,
            upcoming4LabelName, upcoming4LabelDate;




    private Model model = Model.getModel();


    public void manageAllEvents(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/AllEvents.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
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
        model.recentlyAddedEvents();

        add1Button.setText(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 1).getName() + "\n" + model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 1).getDate());
        add2Button.setText(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 2).getName() + "\n" + model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 2).getDate());
        add3Button.setText(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 3).getName() + "\n" + model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 3).getDate());
        add4Button.setText(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 4).getName() + "\n" + model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 4).getDate());
    }

    private void openEventWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/TicketView.fxml"));
            Parent root = loader.load();
            TicketViewController controller = loader.getController();
            controller.ticketViewLaunch();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Event and Ticket Information");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            System.out.println("No selected event to open event ticket window.");
        }
    }

    public void homeEventSelectionUpcoming1(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getObsEvents().get(1));
        openEventWindow();
    }

    public void homeEventSelectionUpcoming2(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getObsEvents().get(1));
        openEventWindow();
    }

    public void homeEventSelectionUpcoming3(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getObsEvents().get(1));
        openEventWindow();
    }

    public void homeEventSelectionUpcoming4(ActionEvent actionEvent) {
        model.setSelectedEvent(model.getObsEvents().get(1));
        openEventWindow();
    }

    public void homeEventSelectionAdd1(ActionEvent actionEvent) {
        model.setSelectedEvent(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 1));
        openEventWindow();
    }

    public void homeEventSelectionAdd2(ActionEvent actionEvent) {
        model.setSelectedEvent(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 2));
        openEventWindow();
    }

    public void homeEventSelectionAdd3(ActionEvent actionEvent) {
        model.setSelectedEvent(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 3));
        openEventWindow();
    }

    public void homeEventSelectionAdd4(ActionEvent actionEvent) {
        model.setSelectedEvent(model.recentlyAddedEvents().get(model.recentlyAddedEvents().size() - 4));
        openEventWindow();
    }
}
