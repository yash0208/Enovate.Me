package com.enovateme.enovateme;

public class cul {
    String link,Video,pdf,name;

    public cul() {
    }

    public cul(String link, String video, String pdf, String name) {
        this.link = link;
        Video = video;
        this.pdf = pdf;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
