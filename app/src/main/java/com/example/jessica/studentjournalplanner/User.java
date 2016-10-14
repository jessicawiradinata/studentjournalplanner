package com.example.jessica.studentjournalplanner;

public class User {
    private String firstName;
    private String lastName;
    private String yearEnrolled;
    private String address;
    private String email;
    private boolean adminPrivilege;

    public User() {
    }

    public User(String firstName, String lastName, String yearEnrolled, String address, String email, boolean adminPrivilege) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearEnrolled = yearEnrolled;
        this.address = address;
        this.email = email;
        this.adminPrivilege = adminPrivilege;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getYearEnrolled() {
        return yearEnrolled;
    }

    public void setYearEnrolled(String yearEnrolled) {
        this.yearEnrolled = yearEnrolled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdminPrivilege() {
        return adminPrivilege;
    }

    public void setAdminPrivilege(boolean adminPrivilege) {
        this.adminPrivilege = adminPrivilege;
    }
}
