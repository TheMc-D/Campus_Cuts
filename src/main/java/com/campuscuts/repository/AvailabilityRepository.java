package com.campuscuts.repository;

import com.campuscuts.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByProviderIdOrderByDayOfWeekAscStartTimeAsc(Long providerId);
}
