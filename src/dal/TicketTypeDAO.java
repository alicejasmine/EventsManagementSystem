package dal;

import be.Event;
import be.SpecialTicket;
import be.SpecialTicketsWrapper;
import be.TicketType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketTypeDAO {

    private DatabaseConnector databaseConnector;

    public TicketTypeDAO() {
        databaseConnector = new DatabaseConnector();
    }

/**
 * method to create a new ticket type in the database **/
    public TicketType createTicketType(TicketType t) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO SpecialTicketType(TicketTypeName) VALUES (?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, t.getTicketTypeName());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new TicketType(id, t.getTicketTypeName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to get a list of all ticket types in the database**/

    public List<TicketType> getAllTicketTypes() {

        ArrayList<TicketType> ticketType = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM SpecialTicketType";
            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    int ticketTypeID = resultSet.getInt("TicketTypeID");
                    String ticketTypeName = resultSet.getString("TicketTypeName");

                    TicketType type = new TicketType(ticketTypeID, ticketTypeName);
                    ticketType.add(type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketType;
    }



}
