package com.example.project_socialmedia.domain.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages;

    private String  username;
    private String  firstName;
    private String  lastName;
    private String  email;
    private String  bio;
    private String  password;

    private Date    createdAt;
    private Date    lastLogin;


    /**
     * User Constructor
     *
     * @param username  String
     * @param firstName String
     * @param lastName  String
     * @param email     String
     * @param password  String
     * @param createdAt Date
     * @param lastLogin Date
     */
    public User(String username, String firstName, String lastName, String email, String password, Date createdAt, Date lastLogin) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    // ==> Custom Function
    /**
     * Update the User last login
     * @param lastLogin Date
     */
    public void updateUserLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void updateUser(String firstName, String lastName, String email, String password) {
        this.firstName =  firstName;
        this.lastName =  lastName;
        this.email =  email;
        this.password = password;
    }
}
