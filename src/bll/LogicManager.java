package bll;

import be.*;
import dal.*;

import java.sql.SQLException;
import java.util.*;

public class LogicManager {
    private EventDAO eventDAO=new EventDAO();
    private AccountsDAO accountsDAO =  new AccountsDAO();

    /**
     * Event CRUD methods.
     */
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

    /**
     * User methods.
     */
    public List<User> getAllUsers(){return accountsDAO.getAllUsers();}
    public void updateUser(User user){accountsDAO.updateUser(user);}
    public void deleteUser(User user){accountsDAO.deleteUser(user.getUserID());}
    public User createUser(User user){return accountsDAO.createUser(user);}

    public User getUser(String userName, String userPass) {
        try {
            return accountsDAO.loginUser(userName, userPass);
        }
        catch (NoSuchElementException e){
            return null;
        }
    }
}
