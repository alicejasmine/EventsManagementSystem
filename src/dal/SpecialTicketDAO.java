package dal;


import be.SpecialTicket;

import java.sql.*;

public class SpecialTicketDAO {

    private DatabaseConnector databaseConnector;

    public SpecialTicketDAO(){databaseConnector=new DatabaseConnector();}


    public SpecialTicket createSpecialTicket(SpecialTicket t) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO SpecialTicket(SpecialTicketID, TicketTypeID, EventID) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, t.getSpecialTicketID());
            statement.setInt(2, t.getTicketTypeID());
            statement.setInt(3, t.getEventID());
            statement.execute();

            return new SpecialTicket(t.getSpecialTicketID(), t.getTicketTypeID(), t.getEventID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
