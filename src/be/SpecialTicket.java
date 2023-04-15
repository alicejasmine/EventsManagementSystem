package be;

public class SpecialTicket {

    private String specialTicketID;
    private int ticketTypeID;
    private int eventID;

    public SpecialTicket(String specialTicketID, int ticketTypeID, int eventID) {
        this.specialTicketID = specialTicketID;
        this.ticketTypeID = ticketTypeID;
        this.eventID = eventID;
    }



    public String getSpecialTicketID() {
        return specialTicketID;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public int getEventID() {
        return eventID;
    }

    @Override
    public String toString() {
        return specialTicketID;

    }
}
