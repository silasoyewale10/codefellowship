package com.soyewale.codefellowship.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    ApplicationUser applicationUser;

    String body;
    public String createdAt;
    boolean kidsAppropriate;

    public long getId() {
        return id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public String getBody() {
        return body;
    }

//    public String getcreatedAt() {
//        return createdAt;
//    }

    public boolean isKidsAppropriate() {
        return kidsAppropriate;
    }
    public Post(ApplicationUser applicationUser, String body, String createdAt, boolean kidsAppropriate) {
        this.applicationUser = applicationUser;
        this.body = body;
        this.createdAt = createdAt;
        this.kidsAppropriate = kidsAppropriate;
    }
    public Post(){

    }
}

