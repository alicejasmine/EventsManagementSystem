package be;

public class SpecialTicket {

    private String SpecialTicketID;
    private int ticketTypeID;
    private int eventID;

    public SpecialTicket(String specialTicketID, int ticketTypeID, int eventID) {
        SpecialTicketID = specialTicketID;
        this.ticketTypeID = ticketTypeID;
        this.eventID = eventID;
    }

    public SpecialTicket(TicketType selectedTicketType, Event selectedEvent) {
    }

    public String getSpecialTicketID() {
        return SpecialTicketID;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public int getEventID() {
        return eventID;
    }
}
