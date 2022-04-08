package com.example.lab16.Models;


import javax.persistence.*;

@Entity
public class follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private  ApplicationUser fromUser;

    @ManyToOne
    private  ApplicationUser toUser;



    public follow() {};

    public follow(ApplicationUser fromUser, ApplicationUser toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
