package be;

public class SpecialTicketsWrapper {

    TicketType ticketType;
    Event event;
    SpecialTicket specialTicket;

    public SpecialTicketsWrapper(TicketType ticketType, Event event, SpecialTicket specialTicket) {
        this.ticketType = ticketType;
        this.event = event;
        this.specialTicket = specialTicket;
    }


    public TicketType getTicketType() {
        return ticketType;
    }

    public Event getEvent() {
        return event;
    }

    public SpecialTicket getSpecialTicket() {
        return specialTicket;
    }
}

