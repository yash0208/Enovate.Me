package com.enovateme.enovateme;

public class tips_pic {
    String link,Name,Field,Url;

    public tips_pic() {
    }

    public tips_pic(String link, String name, String field, String url) {
        this.link = link;
        Name = name;
        Field = field;
        Url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
