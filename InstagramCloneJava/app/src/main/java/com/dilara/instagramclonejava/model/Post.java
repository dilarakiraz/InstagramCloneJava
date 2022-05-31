package com.dilara.instagramclonejava.model;

public class Post {


    public Post(String email, String comment, String downloadUrl) {
        this.email = email;
        this.comment = comment;
        this.downloadUrl = downloadUrl;
    }

    public String email;
    public String comment;
    public String downloadUrl;

}
