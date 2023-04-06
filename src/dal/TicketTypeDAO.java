package dal;

import be.SpecialTicket;
import be.Ticket;
import be.TicketType;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketTypeDAO {

    private DatabaseConnector databaseConnector;

public TicketTypeDAO(){databaseConnector=new DatabaseConnector();}


    public TicketType createTicketType(TicketType t) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO SpecialTicketType(TicketTypeName, MaxQuantity) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, t.getTicketTypeName());
            statement.setInt(2, t.getMaxQuantity());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new TicketType(id, t.getTicketTypeName(), t.getMaxQuantity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<TicketType> getAllTicketTypes() {

        ArrayList<TicketType> ticketType = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM SpecialTicketType";
            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    int ticketTypeID = resultSet.getInt("TicketTypeID");
                    String ticketTypeName = resultSet.getString("TicketTypeName");
                    int maxQuantity = resultSet.getInt("MaxQuantity");

                    TicketType type = new TicketType(ticketTypeID, ticketTypeName, maxQuantity);
                    ticketType.add(type);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketType;
    }


}
