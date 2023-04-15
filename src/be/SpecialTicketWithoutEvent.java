package be;

public class SpecialTicketWithoutEvent {
    private String specialTicketID;
    private int ticketTypeID;

    public SpecialTicketWithoutEvent(String specialTicketID, int ticketTypeID) {
        this.specialTicketID = specialTicketID;
        this.ticketTypeID = ticketTypeID;
    }



    public String getSpecialTicketID() {
        return specialTicketID;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public void setSpecialTicketID(String specialTicketID) {
        this.specialTicketID = specialTicketID;
    }

    public void setTicketTypeID(int ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }

    @Override
    public String toString() {
        return "SpecialTicketWithoutEvent{" +
                "specialTicketID='" + specialTicketID + '\'' +
                ", ticketTypeID=" + ticketTypeID +
                '}';
    }
}
