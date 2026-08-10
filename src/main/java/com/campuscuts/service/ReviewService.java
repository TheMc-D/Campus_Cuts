package com.campuscuts.service;

import com.campuscuts.dto.ReviewForm;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.Review;
import com.campuscuts.entity.User;
import com.campuscuts.repository.ProviderRepository;
import com.campuscuts.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProviderRepository providerRepository;

    public ReviewService(ReviewRepository reviewRepository, ProviderRepository providerRepository) {
        this.reviewRepository = reviewRepository;
        this.providerRepository = providerRepository;
    }

    public List<Review> forProvider(Long providerId) {
        return reviewRepository.findByProviderIdOrderByCreatedAtDesc(providerId);
    }

    @Transactional
    public Review postReview(Provider provider, User author, ReviewForm form) {
        if (reviewRepository.existsByAuthorIdAndProviderId(author.getId(), provider.getId())) {
            throw new IllegalStateException("You have already reviewed this provider");
        }
        Review review = new Review();
        review.setProvider(provider);
        review.setAuthor(author);
        review.setRating(form.getRating());
        review.setBody(form.getBody());
        review = reviewRepository.save(review);

        recomputeRating(provider);
        return review;
    }

    private void recomputeRating(Provider provider) {
        List<Review> reviews = reviewRepository.findByProviderIdOrderByCreatedAtDesc(provider.getId());
        int count = reviews.size();
        double average = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

        provider.setRatingCount(count);
        provider.setAvgRating(BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP));
        providerRepository.save(provider);
    }
}
