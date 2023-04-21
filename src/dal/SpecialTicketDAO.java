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

    /**
     * method to create a special ticket associated with a ticket type and an event, the quantity indicates how many tickets to create
     */
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


    /**
     * this method is used to get the data into the tableview in special tickets overview
     */

    public ObservableList<SpecialTicketOverviewWrapper> getSpecialTicketOverview() throws SQLException {
        ObservableList<SpecialTicketOverviewWrapper> ticketInfoList = FXCollections.observableArrayList();

        String sql = "SELECT stt.TicketTypeName, stt.TicketTypeID, e.Name, COUNT(st.SpecialTicketID) AS AvailableSpecialTickets " +
                "FROM SpecialTicket st " +
                "JOIN SpecialTicketType stt ON st.TicketTypeID = stt.TicketTypeID " +
                "JOIN Event e ON st.EventID = e.EventID " +
                "GROUP BY stt.TicketTypeName,stt.TicketTypeID, e.Name " +
                "UNION " +
                "SELECT stt.TicketTypeName, stt.TicketTypeID, NULL AS EventName, COUNT(stwe.SpecialTicketID) AS AvailableSpecialTickets " +
                "FROM SpecialTicketsWithoutEvent stwe " +
                "JOIN SpecialTicketType stt ON stwe.TicketTypeID = stt.TicketTypeID " +
                "GROUP BY stt.TicketTypeName, stt.TicketTypeID ";


        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                String ticketTypeName = resultSet.getString("TicketTypeName");
                int ticketTypeID = resultSet.getInt("TicketTypeID");
                String eventName = resultSet.getString("Name");
                int availableSpecialTickets = resultSet.getInt("AvailableSpecialTickets");

                Event event = new Event(eventName);

                TicketType ticketType = new TicketType(ticketTypeID, ticketTypeName);
                SpecialTicketOverviewWrapper ticketInfo = new SpecialTicketOverviewWrapper(event, ticketType, availableSpecialTickets);
                ticketInfoList.add(ticketInfo);
                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketInfoList;
    }

    /**
     * this method is used to get the data into the tableview in special tickets
     */

    public ObservableList<SpecialTicketsWrapper> getSpecialTicketsInfo() {
        ObservableList<SpecialTicketsWrapper> specialTickets = FXCollections.observableArrayList();
        String sql = "SELECT tt.TicketTypeName, tt.TicketTypeID, st.SpecialTicketID, e.EventID, e.Name, e.Location, e.Date, e.Time, e.Notes, e.EndTime, e.LocationGuidance, e.FilePath " +
                "FROM SpecialTicket st " +
                "JOIN SpecialTicketType tt ON st.TicketTypeID = tt.TicketTypeID" +
                " JOIN Event e ON st.EventID=e.EventID";
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String ticketTypeName = resultSet.getString("TicketTypeName");
                int ticketTypeID = resultSet.getInt("TicketTypeID");
                String specialTicketID = resultSet.getString("SpecialTicketID");

                int eventID = resultSet.getInt("EventID");
                String eventName = resultSet.getString("Name");
                String eventLocation = resultSet.getString("Location");
                Date eventDate = resultSet.getDate("Date");
                Time eventStart = resultSet.getTime("Time");
                Time eventEnd = resultSet.getTime("EndTime");
                String eventLocationGuidance = resultSet.getString("LocationGuidance");
                String eventNotes = resultSet.getString("Notes");
                String filePath = resultSet.getString("FilePath");

                Event event = new Event(eventID, eventName, eventLocation, eventDate, eventStart, eventNotes, eventEnd, eventLocationGuidance, filePath);
                TicketType ticketType = new TicketType(ticketTypeID, ticketTypeName);
                SpecialTicket specialTicket = new SpecialTicket(specialTicketID, ticketTypeID, event.getId());

                SpecialTicketsWrapper specialTicketsWrapper = new SpecialTicketsWrapper(ticketType, event, specialTicket);
                specialTickets.add(specialTicketsWrapper);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTickets;
    }

    /**
     * method to delete a selected special ticket from database
     */
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

    /**
     * method to create special tickets that are not associated with an event  - the quantity set indicates how many tickets to create
     **/
    public List<SpecialTicketWithoutEvent> createSpecialTicketWithoutEvent(TicketType selectedTicketType, int maxQuantity) {

        List<SpecialTicketWithoutEvent> specialTickets = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO SpecialTicketsWithoutEvent(SpecialTicketID, TicketTypeID) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 1; i <= maxQuantity; i++) {
                String specialTicketID = generateType1UUID().toString();
                statement.setString(1, specialTicketID);
                statement.setInt(2, selectedTicketType.getTicketTypeID());

                statement.executeUpdate();
                specialTickets.add(new SpecialTicketWithoutEvent(specialTicketID, selectedTicketType.getTicketTypeID()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTickets;
    }

    /**
     * method to retrieve information on special tickets without event**/

    public ObservableList<SpecialTicketsWithoutEventWrapper> getSpecialTicketsWithoutEventInfo() {
        ObservableList<SpecialTicketsWithoutEventWrapper> specialTicketsWithoutEvent = FXCollections.observableArrayList();
        String sql = "SELECT tt.TicketTypeName, tt.TicketTypeID, st.SpecialTicketID " +
                "FROM SpecialTicketsWithoutEvent st " +
                "JOIN SpecialTicketType tt ON st.TicketTypeID = tt.TicketTypeID ";

        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String ticketTypeName = resultSet.getString("TicketTypeName");
                String specialTicketID = resultSet.getString("SpecialTicketID");
                int ticketTypeID = resultSet.getInt("TicketTypeID");


                TicketType ticketType = new TicketType(ticketTypeID, ticketTypeName);
                SpecialTicketWithoutEvent specialTicketWithoutEvent = new SpecialTicketWithoutEvent(specialTicketID, ticketTypeID);

                SpecialTicketsWithoutEventWrapper specialTicketWithoutEventWrapper = new SpecialTicketsWithoutEventWrapper(ticketType, specialTicketWithoutEvent);
                specialTicketsWithoutEvent.add(specialTicketWithoutEventWrapper);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialTicketsWithoutEvent;
    }


    /**
     * method to delete a special ticket without event**/
    public void deleteSpecialTicketWithoutEvent(SpecialTicketWithoutEvent ticket) {

        String sql = "DELETE FROM SpecialTicketsWithoutEvent WHERE SpecialTicketID= ? ";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getSpecialTicketID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}


