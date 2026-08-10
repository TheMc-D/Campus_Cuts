package com.campuscuts.repository;

import com.campuscuts.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserIdAndProviderId(Long userId, Long providerId);

    boolean existsByUserIdAndProviderId(Long userId, Long providerId);

    List<Favorite> findByUserId(Long userId);

    void deleteByUserIdAndProviderId(Long userId, Long providerId);
}
