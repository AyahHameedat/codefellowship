package com.example.lab16.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;


@Entity
public class PostUsers {

//    A Post has a body and a createdAt timestamp.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String body;

    @CreationTimestamp
    private Timestamp createdAt;

    public PostUsers() {

    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
    }

    @ManyToOne
    ApplicationUser appUser;

}
