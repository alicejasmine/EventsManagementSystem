package bll;

import be.*;
import dal.EventDAO;

import java.sql.SQLException;
import java.util.List;

public class LogicManager {
    private EventDAO eventDAO=new EventDAO();

    public Event createEvent(Event e) {return eventDAO.createEvent(e);}
    public void deleteEvent(Event e){
        eventDAO.deleteEvent(e.getId());
    }

    public void update(Event e){
        eventDAO.updateEvent(e);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

}