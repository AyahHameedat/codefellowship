package com.example.lab16.Models;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.sql.Date;
import java.util.List;
import java.util.Set;


@Getter
@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    @Column(unique = true)
    private String username;

    private String firsName;
    private String lastName;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String bio;

//    @OneToMany(mappedBy = "cart")
//    Set<Item> items;

    @OneToMany(mappedBy = "appUser")
    Set<PostUsers> post;


    @ManyToMany
    @JoinTable(
            name = "user_user",
            joinColumns = {@JoinColumn(name = "from_id")},
            inverseJoinColumns = {@JoinColumn(name = "to_id")}
    )
    public List<ApplicationUser> following;

    @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
    public List<ApplicationUser> followers;

    public List<ApplicationUser> getFollowing() {
        return following;
    }

    public List<ApplicationUser> getFollowers() {
        return followers;
    }

    public ApplicationUser(String password, String username, String firsName, String lastName, Date dateOfBirth, String bio) {
        this.password = password;
        this.username = username;
        this.firsName = firsName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public ApplicationUser() {
    }

    public Long getId() {
        return id;
    }

    public String getFirsName() {
        return firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public Set<PostUsers> getPost() {
        return post;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


