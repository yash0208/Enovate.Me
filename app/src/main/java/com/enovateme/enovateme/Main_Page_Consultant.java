package com.enovateme.enovateme;

public class Main_Page_Consultant {
    String Name,DP,Field,Id;

    public Main_Page_Consultant() {
    }

    public Main_Page_Consultant(String name, String DP, String field, String id) {
        Name = name;
        this.DP = DP;
        Field = field;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDP() {
        return DP;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
