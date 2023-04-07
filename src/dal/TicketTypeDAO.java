package dal;

import be.SpecialTicket;
import be.SpecialTicketWithTicketType;
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

            if (statement.execute(sql)) {
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


    public ObservableList<SpecialTicketWithTicketType> getSpecialTicketsWithTicketType () {
        ObservableList<SpecialTicketWithTicketType> specialTickets = FXCollections.observableArrayList();
        String sql = "SELECT tt.TicketTypeName, st.SpecialTicketID, e.Name " +
                "FROM SpecialTicket st " +
                "JOIN SpecialTicketType tt ON st.TicketTypeID = tt.TicketTypeID" +
                 " JOIN Event e ON st.EventID=e.EventID";
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    String ticketTypeName = resultSet.getString("TicketTypeName");
                    String specialTicketID = resultSet.getString("SpecialTicketID");
                    String eventName = resultSet.getString("Name");
                    SpecialTicketWithTicketType specialTicket = new SpecialTicketWithTicketType(ticketTypeName, eventName, specialTicketID);
                    specialTickets.add(specialTicket);
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTickets;
    }

}
