package com.enovateme.enovateme;

public class Tips_Class {
    String Link,Name,Url,Des,Issue,Type;

    public Tips_Class() {
    }

    public Tips_Class(String link, String name, String url, String des, String issue, String type) {
        Link = link;
        Name = name;
        Url = url;
        Des = des;
        Issue = issue;
        Type = type;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
