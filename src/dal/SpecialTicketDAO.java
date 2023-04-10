package dal;


import be.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static bll.TicketLogicManager.generateType1UUID;

public class SpecialTicketDAO {

    private DatabaseConnector databaseConnector;

    public SpecialTicketDAO() {
        databaseConnector = new DatabaseConnector();
    }


    public List<SpecialTicket> createSpecialTicket(TicketType selectedTicketType, Event selectedEvent, int maxQuantity) {
        List<SpecialTicket> specialTickets = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO SpecialTicket(SpecialTicketID, TicketTypeID, EventID) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 1; i <= maxQuantity; i++) {
                String specialTicketID = generateType1UUID().toString();
                statement.setString(1, specialTicketID);
                statement.setInt(2, selectedTicketType.getTicketTypeID());
                statement.setInt(3, selectedEvent.getId());
                statement.executeUpdate();
                specialTickets.add(new SpecialTicket(specialTicketID, selectedTicketType.getTicketTypeID(), selectedEvent.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTickets;
    }



    public List<SpecialTicket> getAllSpecialTickets()  {
        ArrayList<SpecialTicket> specialTickets = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM SpecialTickets";
            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    String id = resultSet.getString("SpecialTicketID");
                    int eventID = resultSet.getInt("EventID");
                    int ticketType = resultSet.getInt("TicketTypeID");

                    SpecialTicket ticket = new SpecialTicket(id, eventID, ticketType);
                    specialTickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTickets;
    }



    /**
     * this method is used to get the data into the tableview in special tickets overview*/

    public ObservableList<SpecialTicketOverviewWrapper> getSpecialTicketOverview() throws SQLException {
        ObservableList<SpecialTicketOverviewWrapper> ticketInfoList = FXCollections.observableArrayList();

        String sql = "SELECT stt.TicketTypeName, e.Name, COUNT(st.SpecialTicketID) AS AvailableSpecialTickets " +
                "FROM SpecialTicket st " +
                "JOIN SpecialTicketType stt ON st.TicketTypeID = stt.TicketTypeID " +
                "JOIN Event e ON st.EventID = e.EventID " +
                "GROUP BY stt.TicketTypeName, e.Name";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                String ticketTypeName = resultSet.getString("TicketTypeName");
                String eventName = resultSet.getString("Name");
                int availableSpecialTickets = resultSet.getInt("AvailableSpecialTickets");

                Event event = new Event(eventName);
                TicketType ticketType = new TicketType(ticketTypeName);
                SpecialTicketOverviewWrapper ticketInfo = new SpecialTicketOverviewWrapper(event, ticketType, availableSpecialTickets);
                ticketInfoList.add(ticketInfo);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketInfoList;
    }

    /**
     * this method is used to get the data into the tableview in special tickets*/

    public ObservableList<SpecialTicketsWrapper> getSpecialTicketsInfo() {
        ObservableList<SpecialTicketsWrapper> specialTickets = FXCollections.observableArrayList();
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

                Event event = new Event(eventName);
                TicketType ticketType = new TicketType(ticketTypeName);
                SpecialTicket specialTicket= new SpecialTicket(specialTicketID);

                SpecialTicketsWrapper specialTicketsWrapper = new SpecialTicketsWrapper(ticketType,event, specialTicket);
                specialTickets.add(specialTicketsWrapper);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTickets;
    }

    public void deleteSpecialTicket(SpecialTicket ticket) {
        String sql = "DELETE FROM SpecialTicket WHERE SpecialTicketID= ? ";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getSpecialTicketID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
