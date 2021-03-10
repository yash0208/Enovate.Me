package com.enovateme.enovateme;
public class Inclass {
    String Des;
    String Link;
    String Name;
    String Url;

    public Inclass(String name, String link, String url, String des) {
        this.Name = name;
        this.Link = link;
        this.Url = url;
        this.Des = des;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getLink() {
        return this.Link;
    }

    public void setLink(String link) {
        this.Link = link;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public String getDes() {
        return this.Des;
    }

    public void setDes(String des) {
        this.Des = des;
    }
}
