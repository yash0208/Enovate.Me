package com.enovateme.enovateme;

import java.sql.DataTruncation;

public class idea {
    String Date,Id,Idea;

    public idea() {
    }

    public idea(String date, String id, String idea) {
        Date = date;
        Id = id;
        Idea = idea;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIdea() {
        return Idea;
    }

    public void setIdea(String idea) {
        Idea = idea;
    }
}
