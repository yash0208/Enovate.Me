package com.enovateme.enovateme;

public class CollaborationClass {
    String Business_Address,Business_Pin_code,Contact_Info,Description,Email,Link,Mission_Vision,Name,Pic1,Pic2,Pic3,UserId;

    public CollaborationClass() {
    }

    public CollaborationClass(String business_Address, String business_Pin_code, String contact_Info, String description, String email, String link, String mission_Vision, String name, String pic1, String pic2, String pic3, String userId) {
        Business_Address = business_Address;
        Business_Pin_code = business_Pin_code;
        Contact_Info = contact_Info;
        Description = description;
        Email = email;
        Link = link;
        Mission_Vision = mission_Vision;
        Name = name;
        Pic1 = pic1;
        Pic2 = pic2;
        Pic3 = pic3;
        UserId = userId;
    }

    public String getBusiness_Address() {
        return Business_Address;
    }

    public void setBusiness_Address(String business_Address) {
        Business_Address = business_Address;
    }

    public String getBusiness_Pin_code() {
        return Business_Pin_code;
    }

    public void setBusiness_Pin_code(String business_Pin_code) {
        Business_Pin_code = business_Pin_code;
    }

    public String getContact_Info() {
        return Contact_Info;
    }

    public void setContact_Info(String contact_Info) {
        Contact_Info = contact_Info;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getMission_Vision() {
        return Mission_Vision;
    }

    public void setMission_Vision(String mission_Vision) {
        Mission_Vision = mission_Vision;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getPic2() {
        return Pic2;
    }

    public void setPic2(String pic2) {
        Pic2 = pic2;
    }

    public String getPic3() {
        return Pic3;
    }

    public void setPic3(String pic3) {
        Pic3 = pic3;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
