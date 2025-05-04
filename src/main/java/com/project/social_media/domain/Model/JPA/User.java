package com.project.social_media.domain.Model.JPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_state")
    private userState userState;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_role")
    private userRole userRole;

    @Getter
    @Setter
    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDay;

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

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (lastLogin == null) {
            lastLogin = now;
        }
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    // quan hệ với Relationship
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Relationships> relationshipsAsUser1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Relationships> relationshipsAsUser2 = new ArrayList<>();

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
    public User(userRole userRole, userState userState, String username, String firstName, String lastName, String email, String password, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.userRole = userRole;
        this.userState = userState;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    /**
     * Update the User last login
     *
     * @param lastLogin Date
     */
    public void updateUserLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    // ==> Custom Function

    public enum userRole {
        USER,
        ADMIN,
    }

    public enum userState {
        ACTIVE,
        SUSPENDED,
    }
}
