package gui.model;

import be.*;
import bll.*;
import com.google.zxing.WriterException;
import javafx.collections.*;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.*;

public class Model {
    private ObservableList<Event> events = FXCollections.observableArrayList();
    private ObservableList<Event> recentAddedEvents = FXCollections.observableArrayList();
    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();
    private ObservableList<Ticket> eventFilteredTickets = FXCollections.observableArrayList();
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


    // Getters and Setters
    public ObservableList<Event> getObsEvents() {
        return events;
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

    // loaders
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

    public void loadEventTicketList() {
        loadTicketList();
        eventFilteredTickets().clear();
        eventFilteredTickets();
    }


    //Event methods
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
        ArrayList<Integer> recentEventIDs = new ArrayList<>();
        int id;

        for(Event event: events){
            id = event.getId();
            recentEventIDs.add(id);
        }
        Collections.sort(recentEventIDs);

        for(Event event: events){
            if(recentEventIDs.contains(event.getId())){
                recentAddedEvents.add(event);
            }
        }

        return recentAddedEvents;
    }

    // Ticket methods
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

    public void saveTicket(Event event, Ticket ticket) throws IOException, WriterException {
        tlm.saveTicket(event, ticket);
    }

    public void printTicket(Event event, Ticket ticket) throws IOException, WriterException, PrinterException {
        tlm.printTicket(tlm.writeEventInfoOnTicket(event, ticket));
    }
}

