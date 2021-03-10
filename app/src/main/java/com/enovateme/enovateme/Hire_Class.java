package com.enovateme.enovateme;

public class Hire_Class {
    String Charge,Description,Id,Skill,Url;

    public Hire_Class() {
    }

    public Hire_Class(String charge, String description, String id, String skill, String url) {
        Charge = charge;
        Description = description;
        Id = id;
        Skill = skill;
        Url = url;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSkill() {
        return Skill;
    }

    public void setSkill(String skill) {
        Skill = skill;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
