package com.example.jessica.studentjournalplanner;

/**
 * Created by timothyalfares on 9/27/2016.
 */
public class Event
{
    private String Date;
    private String Description;
    private String Location;
    private String Name;
    private String Time;
    private int id;

    public Event()
    {

    }

    public Event(String date, String description, String location, String name, String time) {
        Date = date;
        Description = description;
        Location = location;
        Name = name;
        Time = time;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
