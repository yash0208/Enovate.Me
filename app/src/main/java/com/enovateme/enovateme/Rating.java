package com.enovateme.enovateme;

public class Rating {
    String name,review,image;
    String  rate;

    public Rating() {
    }

    public Rating(String name, String review, String image, String rate) {
        this.name = name;
        this.review = review;
        this.image = image;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
