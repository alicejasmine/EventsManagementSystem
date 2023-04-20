package dal;

import be.Event;
import dal.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest {
    private EventDAO eventDAO = new EventDAO();


    @Test
    void createEvent() {

        //Arrange
        String expectedName = "Christmas Party";
        String expectedLocation = "EASV Bar";
        Date expectedDate = Date.valueOf("2023-12-20");
        Time expectedTime = Time.valueOf("19:00:00");
        String expectedNotes = "Notes";
        Time expectedEndTime = Time.valueOf("23:00:00");
        String expectedLocationGuidance = "Location Guidance";
        String expectedFilePath = "File Path";

        Event event = new Event(expectedName, expectedLocation, expectedDate, expectedTime, expectedNotes, expectedEndTime, expectedLocationGuidance, expectedFilePath);

        //Act
        Event actualEvent = eventDAO.createEvent(event);

        try {
            //Assert
            assertNotNull(actualEvent);
            assertEquals(expectedName, actualEvent.getName());
            assertEquals(expectedLocation, actualEvent.getLocation());
            assertEquals(expectedDate, actualEvent.getDate());
            assertEquals(expectedTime, actualEvent.getTime());
            assertEquals(expectedNotes, actualEvent.getNotes());
            assertEquals(expectedEndTime, actualEvent.getEndTime());
            assertEquals(expectedLocationGuidance, actualEvent.getLocationGuidance());
            assertEquals(expectedFilePath, actualEvent.getImageFilePath());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void deleteEvent() {
    }

    @Test
    void updateEvent() {
        //Arrange
        int expectedID = 43; //requires to change id for existing event in the database
        String expectedName = "Updated Name";
        String expectedLocation = "Updated Location";
        Date expectedDate = Date.valueOf("2023-04-20");
        Time expectedTime = Time.valueOf("14:00:00");
        String expectedNotes = "Updated Notes";
        Time expectedEndTime = Time.valueOf("16:00:00");
        String expectedLocationGuidance = "Updated Location Guidance";
        String expectedFilePath = "Updated File Path";

        Event event = new Event(expectedID, expectedName, expectedLocation, expectedDate, expectedTime, expectedNotes, expectedEndTime, expectedLocationGuidance, expectedFilePath);

        //Act
        eventDAO.updateEvent(event);
        Event updatedEvent = eventDAO.getEventById(expectedID);

        //Assert
        assertNotNull(updatedEvent);
        assertEquals(expectedName, updatedEvent.getName());
        assertEquals(expectedLocation, updatedEvent.getLocation());
        assertEquals(expectedDate, updatedEvent.getDate());
        assertEquals(expectedTime, updatedEvent.getTime());
        assertEquals(expectedNotes, updatedEvent.getNotes());
        assertEquals(expectedEndTime, updatedEvent.getEndTime());
        assertEquals(expectedLocationGuidance, updatedEvent.getLocationGuidance());
        assertEquals(expectedFilePath, updatedEvent.getImageFilePath());
    }

    @Test
    void getAllEvents() {
        List<Event> listEvent = eventDAO.getAllEvents();
        assertNotNull(listEvent);
        assertEquals(10, listEvent.size()); // requires the size of the table as the first param
    }
}