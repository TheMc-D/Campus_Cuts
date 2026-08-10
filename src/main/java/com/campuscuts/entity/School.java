package com.campuscuts.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schools")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2)
    private String state;

    @OneToMany(mappedBy = "school")
    private List<CampusArea> campusAreas = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    private List<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<CampusArea> getCampusAreas() {
        return campusAreas;
    }

    public List<User> getUsers() {
        return users;
    }
}
