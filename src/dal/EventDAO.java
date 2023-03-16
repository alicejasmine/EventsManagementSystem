package DAL;

import BE.Event;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    private DatabaseConnector databaseConnector;

    public EventDAO() {
        databaseConnector = new DatabaseConnector();
    }


    public static void main(String[] args) throws SQLException {

/* tests CRUD without gui

        EventDAO testEvent = new EventDAO();
        Event event = new Event();
        event.setName("test3");
        event.setLocation("EASV bar");
        event.setDate(Date.valueOf("2023-05-01"));
        event.setTime(Time.valueOf("19:00:00"));
        event.setNotes("free accesss");
        event.setEndTime(Time.valueOf("02:00:00"));
        event.setLocationGuidance("easv bar");
        testEvent.createEvent(event);
        testEvent.deleteEvent(3);
        testEvent.updateEvent(new Event(4, "Updated Event Name", "New Location", java.sql.Date.valueOf("2023-03-18"),
                java.sql.Time.valueOf("19:00:00"), "New notes", java.sql.Time.valueOf("22:00:00"), "New location guidance"));



        List<Event> events = testEvent.getAllEvents();
        for (Event event : events) {
            System.out.println(event.toString());
        }

 */
    }


    //CRUD operations create delete update read events

    /**
     * Method to create a new event in the table Event
     **/

    public Event createEvent(Event event) throws SQLException {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO EVENT(Name, Location, Date, Time, Notes, EndTime, LocationGuidance) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, event.getName());
            statement.setString(2, event.getLocation());
            statement.setDate(3, event.getDate());
            statement.setTime(4, event.getTime());
            statement.setString(5, event.getNotes());
            statement.setTime(6, event.getEndTime());
            statement.setString(7, event.getLocationGuidance());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new Event(id, event.getName(), event.getLocation(), event.getDate(), event.getTime(), event.getNotes(), event.getEndTime(), event.getLocationGuidance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to delete an event from the table Event
     **/

    public void deleteEvent(int id) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Event WHERE EventID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Method to update one or more event fields in the table Event
     **/
    public void updateEvent(Event event) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Event SET Name = COALESCE(?, Name), Location = COALESCE(?, Location), " +
                    "Date = COALESCE(?, Date), Time = COALESCE(?, Time), Notes = COALESCE(?, Notes), " +
                    "EndTime = COALESCE(?, EndTime), LocationGuidance = COALESCE(?, LocationGuidance) " +
                    "WHERE EventID = ?";
//COALESCE used so that attribute is updated only if new value is not null, otherwise old value is left
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, event.getName());
            statement.setString(2, event.getLocation());
            statement.setDate(3, event.getDate());
            statement.setTime(4, event.getTime());
            statement.setString(5, event.getNotes());
            statement.setTime(6, event.getEndTime());
            statement.setString(7, event.getLocationGuidance());
            statement.setInt(8, event.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Method to read all events in the Event table and return them as a list
     **/
    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Event";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("EventID");
                String name = resultSet.getString("Name");
                String location = resultSet.getString("Location");
                Date date = resultSet.getDate("Date");
                Time time = resultSet.getTime("Time");
                String notes = resultSet.getString("Notes");
                Time endTime = resultSet.getTime("EndTime");
                String locationGuidance = resultSet.getString("LocationGuidance");

                Event event = new Event(id, name, location, date, time, notes, endTime, locationGuidance);
                events.add(event);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return events;
    }
}