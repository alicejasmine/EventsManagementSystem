package be;

public class SpecialTicketWithTicketType {


    private String specialTicketID;
    private String name;
    private String ticketTypeName;

    public SpecialTicketWithTicketType( String ticketTypeName, String name,String specialTicketID) {

        this.name = name;
        this.ticketTypeName = ticketTypeName;
        this.specialTicketID = specialTicketID;
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