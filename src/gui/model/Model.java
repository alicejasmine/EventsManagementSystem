package gui.model;

import be.*;
import bll.*;
import javafx.collections.*;

public class Model {
    private final ObservableList<Event> events = FXCollections.observableArrayList();

    private LogicManager bll = new LogicManager();

    public void loadEventList() {
        events.clear();
        events.addAll(bll.getAllEvents());
    }

    public ObservableList<Event> getObsEvents(){return events;}
}
