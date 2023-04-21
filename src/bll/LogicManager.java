package bll;

import be.*;
import dal.*;

import java.sql.SQLException;
import java.util.*;

public class LogicManager {

    private EventDAO eventDAO=new EventDAO();
    private AccountsDAO accountsDAO =  new AccountsDAO();

    private AdminDAO adminDAO = new AdminDAO();

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
    public void createUser(User user){accountsDAO.createUser(user);}

    public User getUser(String userName, String userPass) {
        List<User> users = new ArrayList<>(getAllUsers()); // list of users
        List<Admin> admins = new ArrayList<>(getAllAdmins()); // list of admins

        User currentUser = null;
        for (User user:users) {
            if (user.getUserName().equals(userName)){ // we check all of our users to see if any have this user name
                if(user.getUserPass().equals(userPass)){ // if we do have a user with the username, we compare the password
                    currentUser = user; // if user and pass match, we set our user.
                    break;
                }
            }
        }


        if(currentUser !=null){
            for (Admin admin: admins) {
                if(currentUser.getUserID() == admin.getUserID()){ // we check the userID against the userIDs stored in the admin table, if it is in the table the user is an admin.
                    currentUser.setAdmin(true);
                    break;
                }
            }
        }

        return currentUser;
    }

    /**
     * Admin methods.
     */
    public List<Admin> getAllAdmins(){return adminDAO.getAllAdmins();}

    public void createAdmin(Admin admin){adminDAO.createAdmin(admin);}
}
