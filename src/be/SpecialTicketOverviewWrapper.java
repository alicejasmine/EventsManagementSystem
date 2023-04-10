package be;

public class SpecialTicketOverviewWrapper {
    Event event;
    TicketType ticketType;
    private int availableTickets;

    public SpecialTicketOverviewWrapper(Event event, TicketType ticketType, int availableTickets) {
        this.event = event;
        this.ticketType = ticketType;
        this.availableTickets=availableTickets;
    }

    public Event getEvent() {
        return event;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}

