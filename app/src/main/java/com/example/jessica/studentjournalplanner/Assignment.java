package com.example.jessica.studentjournalplanner;

/**
 * Created by Jessica on 9/29/2016.
 */

public class Assignment {
    private String Name;
    private String Subject;
    private String dueDate;
    private String Description;

    public Assignment() {
    }

    public Assignment(String name, String subject, String dueDate, String description) {
        Name = name;
        Subject = subject;
        this.dueDate = dueDate;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public String getSubject() {
        return Subject;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
