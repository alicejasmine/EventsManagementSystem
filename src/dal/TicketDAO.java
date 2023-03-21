package dal;

import be.Event;
import be.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private DatabaseConnector databaseConnector;

    public TicketDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public Ticket createTicket(Ticket ticket) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Tickets(TicketID, CustomerName, CustomerEmail, EventID) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ticket.getTicketID());
            statement.setString(2, ticket.getCustomerName());
            statement.setString(3, ticket.getCustomerEmail());
            statement.setInt(4, ticket.getEventID());

            statement.execute();

            return new Ticket(ticket.getTicketID(), ticket.getCustomerName(), ticket.getCustomerEmail(), ticket.getEventID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
