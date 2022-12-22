package com.example.wagba.Models;

public class User {
    public static final String UserSharedPref="USER_SHARED_PREF";
    String Email,FirstName,LastName;

    public User(String email, String firstName, String lastName) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
