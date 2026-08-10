package com.campuscuts.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/** Maps to the "services" table — named Offering in code to avoid colliding with Spring's @Service stereotype. */
@Entity
@Table(name = "services")
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer durationMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
