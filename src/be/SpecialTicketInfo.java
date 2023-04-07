package be;

public class SpecialTicketInfo {

        private String ticketTypeName;
        private String name;
        private int soldSpecialTickets;
        private int availableSpecialTickets;

        public SpecialTicketInfo(String ticketTypeName, String name, int soldTickets, int availableTickets) {
            this.ticketTypeName = ticketTypeName;
            this.name = name;
            this.soldSpecialTickets = soldTickets;
            this.availableSpecialTickets = availableTickets;
        }

        public String getTicketTypeName() {
            return ticketTypeName;
        }

        public String getName() {
            return name;
        }

        public int getSoldSpecialTickets() {
            return soldSpecialTickets;
        }

        public int getAvailableSpecialTickets() {
            return availableSpecialTickets;
        }
    }

