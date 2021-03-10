package com.enovateme.enovateme;

public class User {
    String UserId,Name,Gender,Email,Type;

    public User() {
    }

    public User(String userId, String name, String gender, String email, String type) {
        UserId = userId;
        Name = name;
        Gender = gender;
        Email = email;
        Type = type;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
