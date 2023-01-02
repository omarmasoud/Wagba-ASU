package com.example.wagba.Models;

public class User {
    public static final String UserSharedPref="USER_SHARED_PREF";
    String Email;
    String FirstName;
    String LastName;
    String UID;
    String Gender;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    String PhoneNumber;
    String age;

    public static String getUserSharedPref() {
        return UserSharedPref;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public User(String email, String firstName, String lastName, String UID, String gender, String age) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        this.UID = UID;
        Gender = gender;
        this.age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public User(String email, String firstName, String lastName,String UID) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        this.UID=UID;
        this.age="20";
        this.Gender="blank";
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
