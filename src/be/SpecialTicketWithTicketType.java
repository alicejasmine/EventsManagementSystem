package be;

public class SpecialTicketWithTicketType {


    private String specialTicketID;
    private String name;
    private String ticketTypeName;

    public SpecialTicketWithTicketType(String specialTicketID, String name, String ticketTypeName) {
        this.specialTicketID = specialTicketID;
        this.name = name;
        this.ticketTypeName = ticketTypeName;
    }

    public String getSpecialTicketID() {
        return specialTicketID;
    }

    public String getName() {
        return name;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }
}