package com.campuscuts.entity;

import com.campuscuts.entity.enums.Role;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Provider provider;

    @OneToMany(mappedBy = "student")
    private List<Conversation> conversationsAsStudent = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "reporter")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Provider getProvider() {
        return provider;
    }

    public List<Conversation> getConversationsAsStudent() {
        return conversationsAsStudent;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Report> getReports() {
        return reports;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }
}
