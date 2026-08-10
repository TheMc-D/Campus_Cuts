package com.campuscuts.entity;

import com.campuscuts.entity.enums.ServiceType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_area_id", nullable = false)
    private CampusArea campusArea;

    @Column(nullable = false)
    private String displayName;

    @Lob
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;

    /** Free-text location detail (e.g. "Suite 204") — no dedicated Building entity for now. */
    private String locationDetail;

    @Column(precision = 3, scale = 2)
    private BigDecimal avgRating;

    @Column(nullable = false)
    private int ratingCount = 0;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offering> offerings = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "provider")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "provider")
    private List<Conversation> conversations = new ArrayList<>();

    @OneToMany(mappedBy = "provider")
    private List<Favorite> favoritedBy = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CampusArea getCampusArea() {
        return campusArea;
    }

    public void setCampusArea(CampusArea campusArea) {
        this.campusArea = campusArea;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public List<Favorite> getFavoritedBy() {
        return favoritedBy;
    }
}
