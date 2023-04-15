package be;

public class SpecialTicketsWithoutEventWrapper {

    TicketType ticketType;

    SpecialTicketWithoutEvent specialTicketWithoutEvent;


    public SpecialTicketsWithoutEventWrapper(TicketType ticketType, SpecialTicketWithoutEvent specialTicketWithoutEvent) {
        this.ticketType = ticketType;
        this.specialTicketWithoutEvent = specialTicketWithoutEvent;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public SpecialTicketWithoutEvent getSpecialTicketWithoutEvent() {
        return specialTicketWithoutEvent;
    }
}

