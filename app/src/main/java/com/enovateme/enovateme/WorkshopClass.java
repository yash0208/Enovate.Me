package com.enovateme.enovateme;

public class WorkshopClass {
    String Name,Image,Teacher,Background,Id;

    public WorkshopClass() {
    }

    public WorkshopClass(String name, String image, String teacher, String background, String id) {
        Name = name;
        Image = image;
        Teacher = teacher;
        Background = background;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getBackground() {
        return Background;
    }

    public void setBackground(String background) {
        Background = background;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
