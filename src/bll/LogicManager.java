package bll;

import be.Event;
import DAL.EventDAO;

import java.sql.SQLException;
import java.util.List;

public class LogicManager {
    private EventDAO eventDAO=new EventDAO();

    public Event createEvent(Event e) throws SQLException {
        return eventDAO.createEvent(e);
    }
    public void deleteEvent(Event e){
        eventDAO.deleteEvent(e.getId());
    }

    public void update(Event e){
        eventDAO.updateEvent(e);
    }

    public List<Event> getAllEvents() throws SQLException {
        return eventDAO.getAllEvents();
    }

}
