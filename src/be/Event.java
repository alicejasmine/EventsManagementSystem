package be;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int id;
    private String name;
    private String location;
    private java.sql.Date date;
    private Time time;
    private String notes;
    private Time endTime;
    private String locationGuidance;

    public Event(int id, String name, String location, java.sql.Date date, Time time, String notes, Time endTime, String locationGuidance) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.endTime = endTime;
        this.locationGuidance = locationGuidance;
    }



    public Event(String name, String location, Date date, Time time, String notes, Time endTime, String locationGuidance) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.endTime = endTime;
        this.locationGuidance = locationGuidance;
    }

    public Event(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getNotes() {
        return notes;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getLocationGuidance() {
        return locationGuidance;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setLocationGuidance(String locationGuidance) {
        this.locationGuidance = locationGuidance;
    }

    @Override
    public String toString() {
        return name;
    }
}
