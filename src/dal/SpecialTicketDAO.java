package dal;


import be.SpecialTicket;
import be.SpecialTicketInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialTicketDAO {

    private DatabaseConnector databaseConnector;

    public SpecialTicketDAO() {
        databaseConnector = new DatabaseConnector();
    }


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


    public ObservableList<SpecialTicketInfo> getSpecialTicketInfo() throws SQLException {
        ObservableList<SpecialTicketInfo> ticketInfoList = FXCollections.observableArrayList();

        String sql = "SELECT stt.TicketTypeName, e.Name, COUNT(st.SpecialTicketID) AS SoldSpecialTickets, stt.MaxQuantity - COUNT(st.SpecialTicketID) AS AvailableSpecialTickets " +
                "FROM SpecialTicket st " +
                "INNER JOIN SpecialTicketType stt ON st.TicketTypeID = stt.TicketTypeID " +
                "INNER JOIN Event e ON st.EventID = e.EventID " +
                "GROUP BY stt.TicketTypeName, e.Name, stt.MaxQuantity";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                String ticketTypeName = resultSet.getString("TicketTypeName");
                String eventName = resultSet.getString("Name");
                int soldSpecialTickets = resultSet.getInt("SoldSpecialTickets");
                int availableSpecialTickets = resultSet.getInt("AvailableSpecialTickets");

                SpecialTicketInfo ticketInfo = new SpecialTicketInfo(ticketTypeName, eventName, soldSpecialTickets, availableSpecialTickets);
                ticketInfoList.add(ticketInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketInfoList;
    }

}
