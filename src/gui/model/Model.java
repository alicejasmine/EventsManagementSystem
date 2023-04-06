package gui.model;

import be.*;
import bll.*;
import com.google.zxing.WriterException;
import javafx.collections.*;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.time.*;
import java.util.*;

public class Model {
    private ObservableList<Event> events = FXCollections.observableArrayList();

    public ObservableList<Event> getRecentAddedEvents() {
        return recentAddedEvents;
    }

    public ObservableList<Event> getUpcomingEvents() {
        return upcomingEvents;
    }

    private ObservableList<Event> recentAddedEvents = FXCollections.observableArrayList();
    private ObservableList<Event> upcomingEvents = FXCollections.observableArrayList();
    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();
    private ObservableList<Ticket> eventFilteredTickets = FXCollections.observableArrayList();
    private ObservableList<User> userObs = FXCollections.observableArrayList();
    private ObservableList<Integer> hoursTime = FXCollections.observableArrayList();
    private ObservableList<Integer> minsTime = FXCollections.observableArrayList();


    private final int[] hours = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    private final int[] mins = {0, 15, 30, 45};

    private LogicManager bll = new LogicManager();
    private TicketLogicManager tlm = new TicketLogicManager();

    // to create a singleton for our model.
    private static Model model;
    private Event selectedEvent;
    private Ticket selectedTicket;

    private User currentUser;


    /**
     * Getters and setters.
     */
    public ObservableList<Event> getObsEvents() {
        return events;
    }

    public ObservableList<User> getObsUser() {
        return userObs;
    }

    public ObservableList<Ticket> getObsTickets() {
        return tickets;
    }

    public ObservableList<Integer> getHoursTime() {
        return hoursTime;
    }

    public ObservableList<Integer> getMinsTime() {
        return minsTime;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Loading methods.
     */
    public void loadTime() {
        hoursTime.clear();
        minsTime.clear();

        for (int hour : hours) {
            hoursTime.add(hour);
        }
        for (int min : mins) {
            minsTime.add(min);
        }
    }

    public void loadEventList() {
        events.clear();
        events.addAll(bll.getAllEvents());
    }

    public void loadTicketList() {
        tickets.clear();
        tickets.addAll(tlm.getAllTickets());
    }

    public void loadUserList() {
        userObs.clear();
        userObs.addAll(bll.getAllUsers());
    }

    public void loadEventTicketList() {
        loadTicketList();
        if(selectedEvent != null){
            eventFilteredTickets().clear();
            eventFilteredTickets();
        }
    }
    public void loadUpcoming(){
        upcomingEvents.clear();
        upcomingEvents();
    }
    public void loadRecentlyAdded(){
        recentAddedEvents.clear();
        recentlyAddedEvents();
    }

    /**
     * Event methods.
     */
    public void addEvent(Event event) {
        bll.createEvent(event);
        loadEventList();
    }

    public void deleteEvent(Event event) {
        bll.deleteEvent(event);
        loadEventList();
    }

    public void updateEvent(Event event) {
        bll.update(event);
        loadEventList();
    }
    public ObservableList<Event> recentlyAddedEvents(){
        List<Event> recentEventIDs = new ArrayList<>();

        recentEventIDs.addAll(events);

        Collections.sort(recentEventIDs, Comparator.comparing(Event::getId).reversed());

        recentAddedEvents.addAll(recentEventIDs);

        return recentAddedEvents;
    }
    public ObservableList<Event> upcomingEvents(){
        List<Event> upcomingDates = new ArrayList<>();
        Date todayDate = Date.from(Instant.now());

        for(Event event:events){
            if(event.getDate().after(todayDate)){
                upcomingDates.add(event);
            }
        }
        Collections.sort(upcomingDates, Comparator.comparing(Event::getDate));

        upcomingEvents.addAll(upcomingDates);

        for(Event event: upcomingDates){
            if(!upcomingEvents.contains(event)){
                upcomingEvents.add(event);
            }
        }

        return upcomingEvents;
    }

    /**
     * Ticket methods
     */
    public void addTicket(String customerName, String customerEmail, int eventID) {
        tlm.crateTicket(customerName, customerEmail, eventID);
        loadTicketList();
    }

    public ObservableList<Ticket> eventFilteredTickets() {
        eventFilteredTickets.clear();
        for (Ticket ticket : tickets) {
            if (ticket.getEventID() == selectedEvent.getId()) {
                eventFilteredTickets.add(ticket);
            }
        }
        return eventFilteredTickets;
    }

    public void saveTicket(Event event, Ticket ticket)  {
        tlm.saveTicket(event, ticket);
    }


    public void printTicket(Event event, Ticket ticket) {
        tlm.printTicket(tlm.writeEventInfoOnTicket(event, ticket));
    }

    /**
     * User methods.
     */
    public void createUser(User user) {
        bll.createUser(user);
        loadUserList();
    }

    public void deleteUser(User user){
        bll.deleteUser(user);
        loadUserList();
    }

    public void updateUser(User user){
        bll.updateUser(user);
        loadUserList();
    }

    public void loginUser(String userName, String userPass) {
        currentUser = bll.getUser(userName, userPass);
    }


    public void search(String query, int idOfEvent) {
        eventFilteredTickets.clear();
        eventFilteredTickets.addAll(tlm.searchTickets(query, idOfEvent));
    }


}

