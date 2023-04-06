package be;

public class TicketType {
    private int ticketTypeID;
    private String ticketTypeName;
    private int maxQuantity;

    public TicketType(int ticketTypeID, String ticketTypeName, int maxQuantity) {
        this.ticketTypeID = ticketTypeID;
        this.ticketTypeName = ticketTypeName;
        this.maxQuantity = maxQuantity;
    }

    public TicketType(String ticketTypeName, int maxQuantity) {
        this.ticketTypeName = ticketTypeName;
        this.maxQuantity = maxQuantity;
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

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @Override
    public String toString() {
        return ticketTypeName ;
    }
}
