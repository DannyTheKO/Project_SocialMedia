package com.example.project_socialmedia.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String bio;
    private String profileImageUrl;
    private String bannerImageUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

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
    public User(String username, String firstName, String lastName, String email, String password, LocalDateTime createdAt, LocalDateTime lastLogin) {
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
     *
     * @param lastLogin Date
     */
    public void updateUserLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
