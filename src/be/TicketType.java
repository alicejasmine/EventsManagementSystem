package be;

public class TicketType {
    private int ticketTypeID;
    private String ticketTypeName;


    public TicketType(int ticketTypeID, String ticketTypeName) {
        this.ticketTypeID = ticketTypeID;
        this.ticketTypeName = ticketTypeName;
    }

    public TicketType(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }



    public int getTicketTypeID() {
        return ticketTypeID;
    }


    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    @Override
    public String toString() {
        return ticketTypeName ;
    }
}
