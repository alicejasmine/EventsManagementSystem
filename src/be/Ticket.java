package be;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class Ticket {

    private String ticketID;
    private String customerName;
    private String customerEmail;
    private int eventID;

    public Ticket(String ticketID, String customerName, String customerEmail, int eventID) {
        this.ticketID = ticketID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.eventID = eventID;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getEventID() {
        return eventID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
