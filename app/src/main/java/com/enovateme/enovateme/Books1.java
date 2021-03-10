package com.enovateme.enovateme;

public class Books1 {
    String Pic,Genre,Link,Type;

    public Books1() {
    }

    public Books1(String pic, String genre, String link, String type) {
        Pic = pic;
        Genre = genre;
        Link = link;
        Type = type;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPic() {
        return Pic;
    }

    public String getGenre() {
        return Genre;
    }

    public String getLink() {
        return Link;
    }

    public String getType() {
        return Type;
    }
}
