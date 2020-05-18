package com.example.admin.agency;

public class ReviewUser {
    String reviewID;
    String title;
    String reviewerName;
    String contents;

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public ReviewUser(String reviewID, String title, String reviewerName, String contents) {
        this.reviewID = reviewID;
        this.title = title;
        this.reviewerName = reviewerName;
        this.contents = contents;
    }
}
