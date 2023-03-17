package gui.model;

import be.*;
import bll.*;
import javafx.collections.*;

public class Model {
    private final ObservableList<Event> events = FXCollections.observableArrayList();

    private LogicManager bll = new LogicManager();

    // to create a singleton for our model.
    private static Model model;

    public static Model getModel(){
        if(model == null)
            model = new Model();
        return model;
    }

    public void loadEventList() {
        events.clear();
        events.addAll(bll.getAllEvents());
    }

    public ObservableList<Event> getObsEvents(){return events;}
}
