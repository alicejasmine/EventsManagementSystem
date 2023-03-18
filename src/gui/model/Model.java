package gui.model;

import be.*;
import bll.*;
import javafx.collections.*;

public class Model {
    private ObservableList<Event> events = FXCollections.observableArrayList();
    private ObservableList<Integer> hoursTime = FXCollections.observableArrayList();



    private ObservableList<Integer> minsTime = FXCollections.observableArrayList();

    private final int[] hours = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
    private final int[] mins = {0,15,30,45};

    private LogicManager bll = new LogicManager();

    // to create a singleton for our model.
    private static Model model;

    public static Model getModel(){
        if(model == null){
            model = new Model();}
        return model;
    }

    public void loadEventList() {
        events.clear();
        events.addAll(bll.getAllEvents());
    }

    public void loadTime(){
       hoursTime.clear();
       minsTime.clear();

       for (int hour:hours) {hoursTime.add(hour);}
       for (int min:mins) {minsTime.add(min);}
    }

    public void addEvent(Event event){
        bll.createEvent(event);
        loadEventList();
    }

    public void deleteEvent(Event event){
        bll.deleteEvent(event);
        loadEventList();
    }

    public void updateEvent(Event event){
        bll.update(event);
        loadEventList();
    }

    public ObservableList<Event> getObsEvents(){return events;}
    public ObservableList<Integer> getHoursTime() {return hoursTime;}
    public ObservableList<Integer> getMinsTime() {return minsTime;}
}
