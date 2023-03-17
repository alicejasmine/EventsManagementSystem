package gui.controller;

import be.*;
import be.Event;
import gui.model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class NewEventViewController {

    @FXML
    private TextField neNameTF, neLocationTF, neDateTF, neTimeTF, neEndTimeTF;
    @FXML
    private TextArea neNotesTF, neLocationInfoTF;
    @FXML
    private Button neCreateButton, neCancelButton;


    private Model model = Model.getModel();


    public void createEvent(ActionEvent actionEvent) {
    }

    public void cancelNEwindow(ActionEvent actionEvent) {
    }


}
