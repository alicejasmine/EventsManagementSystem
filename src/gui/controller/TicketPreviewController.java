package gui.controller;

import be.Event;
import be.SpecialTicket;
import be.Ticket;
import be.TicketType;
import gui.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketPreviewController implements Initializable {
    private Model model = Model.getModel();
    @FXML
    private ImageView ticketImageView;

    private Event selectedEvent;
    private Ticket selectedTicket;
    private TicketType selectedTicketType;

    private SpecialTicket selectedSpecialTicket;


    @FXML
    private void closeTicketPreview(ActionEvent actionEvent) {
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void setEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public void setTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;

    }

    public void setSpecialTicket(SpecialTicket selectedTicket) {
        this.selectedSpecialTicket = selectedTicket;

    }

    public void renderTicket() {
        if (selectedTicket != null) {
            ticketImageView.setImage(model.createTicketPreview(selectedEvent, selectedTicket).getImage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void setTicketType(TicketType selectedTicketType) {
        this.selectedTicketType = selectedTicketType;
    }

    public void renderSpecialTicket() {
        if (selectedSpecialTicket != null) {
            ticketImageView.setImage(model.createSpecialTicketPreview(selectedEvent, selectedSpecialTicket, selectedTicketType).getImage());
        }
    }
}
