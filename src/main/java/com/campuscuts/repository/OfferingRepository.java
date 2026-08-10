package com.campuscuts.repository;

import com.campuscuts.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferingRepository extends JpaRepository<Offering, Long> {

    List<Offering> findByProviderId(Long providerId);
}
