package gui.model;

import be.*;
import bll.*;
import javafx.collections.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.*;
import java.util.*;

public class Model {

    private ObservableList<Event> events = FXCollections.observableArrayList();
    private final ObservableList<SpecialTicketsWrapper> specialTickets = FXCollections.observableArrayList();
    private final ObservableList<SpecialTicketsWithoutEventWrapper> specialTicketsWithoutEvent = FXCollections.observableArrayList();
    private final ObservableList<SpecialTicketOverviewWrapper> specialTicketsOverview=FXCollections.observableArrayList();

    private final ObservableList<TicketType> ticketType = FXCollections.observableArrayList();

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


    private final int[] hours = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24}; // we are using a 24-hour clock for our time selections
    private final int[] mins = {0, 15, 30, 45}; // we are diving the event starting times into hour quarters.

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

    public static Model getModel() { // this is how we fetch the single instance of our model.
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public User getCurrentUser() {
        return currentUser;
    } // the current user logged in

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Loading methods.
     * All of these methods are clearing and filling our lists.
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


    public void loadTicketTypeList() {
        ticketType.clear();
        ticketType.addAll(tlm.getTicketTypes());
    }

    public void loadUserList() {
        userObs.clear();
        userObs.addAll(bll.getAllUsers());
    }

    public void loadEventTicketList() {
        loadTicketList();
        if (selectedEvent != null) {
            eventFilteredTickets().clear();
            eventFilteredTickets();
        }
    }

    public void loadUpcoming() {
        upcomingEvents.clear();
        upcomingEvents();
    }

    public void loadRecentlyAdded() {
        recentAddedEvents.clear();
        recentlyAddedEvents();
    }

    public void loadSpecialTicket(){
        specialTickets.clear();
        specialTickets.addAll(tlm.getSpecialTicketsInfo());
    }

    public void loadSpecialTicketWithoutEvent(){
        specialTicketsWithoutEvent.clear();
        specialTicketsWithoutEvent.addAll(tlm.getSpecialTicketsWithoutEventInfo());
    }

    public void loadSpecialTicketOverview(){
        specialTicketsOverview.clear();
        specialTicketsOverview.addAll(tlm.getSpecialTicketOverview());
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

    public ObservableList<Event> recentlyAddedEvents() {// we are sorting our events by event ID and making the highest IDs first. This assures we have the 4 most recently created events.
        List<Event> recentEventIDs = new ArrayList<>(events);
        recentEventIDs.sort(Comparator.comparing(Event::getId).reversed());
        recentAddedEvents.addAll(recentEventIDs);
        return recentAddedEvents;
    }


    public ObservableList<Event> upcomingEvents() {
        List<Event> upcomingDates = new ArrayList<>();
        Date todayDate = Date.from(Instant.now());

        for (Event event : events) {// we are checking if the event is after todays date
            if (event.getDate().after(todayDate)) {
                upcomingDates.add(event);
            }
        }
        Collections.sort(upcomingDates, Comparator.comparing(Event::getDate)); //if there are upcoming events we are sorting them by date

        upcomingEvents.addAll(upcomingDates);

        for (Event event : upcomingDates) {
            if (!upcomingEvents.contains(event)) {// if we have events we are adding them to a new list
                upcomingEvents.add(event);
            }
        }

        return upcomingEvents; // return the list of sorted events.
    }

    /**
     * Ticket methods
     */
    public void addTicket(String customerName, String customerEmail, int eventID) {
        tlm.crateTicket(customerName, customerEmail, eventID);
        loadTicketList();
    }

    public ObservableList<Ticket> eventFilteredTickets() {//makes a list of tickets for the event that is currently selected.
        eventFilteredTickets.clear();

        if (selectedEvent != null) {
            eventFilteredTickets.addAll(tlm.getEventFilteredTickets(selectedEvent.getId()));
        }

        return eventFilteredTickets;
    }

    public void saveTicket(Event event, Ticket ticket) {
        tlm.saveTicket(event, ticket);
    }


    public void printTicket(Event event, Ticket ticket) {
        tlm.printTicket(tlm.writeEventInfoOnTicket(event, ticket));
    }



    public ImageView createTicketPreview(Event event, Ticket ticket) {
        try {
            return tlm.createTicketPreview(event,ticket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSpecialTicket(Event event, SpecialTicket specialTicket, TicketType ticketType) {
          tlm.saveSpecialTicket(event, specialTicket,ticketType);
    }


    public void printSpecialTicket(Event event, SpecialTicket specialTicket, TicketType ticketType) {
        tlm.printSpecialTicket(tlm.writeEventInfoOnSpecialTicket(event, specialTicket,ticketType));
    }



    public ImageView createSpecialTicketPreview(Event event, SpecialTicket specialTicket, TicketType ticketType) {
        try {
            return tlm.createSpecialTicketPreview(event,specialTicket, ticketType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageView createSpecialTicketWithoutEventPreview(SpecialTicketWithoutEvent selectedSpecialTicket, TicketType selectedTicketType) {
        try {
            return tlm.createSpecialTicketWithoutEventPreview(selectedSpecialTicket, selectedTicketType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } }

    public void deleteTicket (Ticket ticket) {
        tlm.deleteTicket(ticket);
        loadEventTicketList();
    }

    /**
     * User methods.
     */
    public void createUser(User user) {
        bll.createUser(user);
        loadUserList();
    }

    public void deleteUser(User user) {
        bll.deleteUser(user);
        loadUserList();
    }

    public void updateUser(User user) {
        bll.updateUser(user);
        loadUserList();
    }

    public void loginUser(String userName, String userPass) {
        currentUser = bll.getUser(userName, userPass);
    }

    /**
     * Admin Methods
     */
    public void createAdmin(Admin admin){bll.createAdmin(admin);}


    /**
     * Method for searching tickets, clears the list and adds all results to given list.
     */
    public void search(String query, int idOfEvent) {
        eventFilteredTickets.clear();
        eventFilteredTickets.addAll(tlm.searchTickets(query, idOfEvent));
    }



    public void addTicketType(String ticketTypeName) {
        tlm.addTicketType(ticketTypeName);
        loadTicketTypeList();
    }



    public void createSpecialTicket(TicketType selectedTicketType, Event selectedEvent, int maxQuantity) {

        tlm.createSpecialTicket(selectedTicketType, selectedEvent, maxQuantity);

    }

    public void createSpecialTicketWithoutEvent(TicketType selectedTicketType, int maxQuantity) {

        tlm.createSpecialTicketWithoutEvent(selectedTicketType, maxQuantity);

    }

    public ObservableList<SpecialTicketsWrapper> getObsSpecialTickets() {
        return specialTickets;
    }

    public ObservableList<SpecialTicketsWithoutEventWrapper> getObsSpecialTicketsWithoutEvent() {
        return specialTicketsWithoutEvent;
    }

    public ObservableList<SpecialTicketOverviewWrapper> getObsSpecialTicketsOverview() {
        return specialTicketsOverview;
    }
    public ObservableList<TicketType> getTicketTypes() {
        return ticketType;}

    


    public void deleteSpecialTicket (SpecialTicket ticket) {
        tlm.deleteSpecialTicket(ticket);
        loadSpecialTicket();
    }

    public ObservableList<SpecialTicketsWrapper> getSpecialTicketsInfo() {
        return tlm.getSpecialTicketsInfo();
    }

    public ObservableList<SpecialTicketOverviewWrapper> getSpecialTicketOverviewInfo() {
        return tlm.getSpecialTicketOverview();
    }


    public void deleteSpecialTicketWithoutEvent(SpecialTicketWithoutEvent specialTicketWithoutEvent) {
        tlm.deleteSpecialTicketWithouEvent(specialTicketWithoutEvent);
        loadSpecialTicketWithoutEvent();
    }


    public void saveSpecialTicketWithoutEvent(SpecialTicketWithoutEvent selectedSpecialTicket, TicketType selectedTicketType) {
        tlm.saveSpecialTicketWithoutEvent(selectedSpecialTicket,selectedTicketType);
    }

    public void printSpecialTicketWithoutEvent(SpecialTicketWithoutEvent selectedSpecialTicket, TicketType selectedTicketType) {
        tlm.printTicket(tlm.writeInfoOnSpecialTicketWithoutEvent(selectedSpecialTicket,selectedTicketType));
    }
}


