package com.campuscuts.repository;

import com.campuscuts.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProviderIdOrderByCreatedAtDesc(Long providerId);

    Optional<Review> findByAuthorIdAndProviderId(Long authorId, Long providerId);

    boolean existsByAuthorIdAndProviderId(Long authorId, Long providerId);
}
