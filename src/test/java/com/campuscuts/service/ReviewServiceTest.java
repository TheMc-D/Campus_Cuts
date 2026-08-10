package com.campuscuts.service;

import com.campuscuts.dto.ReviewForm;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.Review;
import com.campuscuts.entity.User;
import com.campuscuts.repository.ProviderRepository;
import com.campuscuts.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void postReview_rejectsDuplicateReviewFromSameAuthor() {
        Provider provider = new Provider();
        provider.setId(1L);
        User author = new User();
        author.setId(2L);

        when(reviewRepository.existsByAuthorIdAndProviderId(2L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> reviewService.postReview(provider, author, new ReviewForm()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void postReview_recomputesProviderAverageRating() {
        Provider provider = new Provider();
        provider.setId(1L);
        User author = new User();
        author.setId(2L);

        ReviewForm form = new ReviewForm();
        form.setRating(4);

        when(reviewRepository.existsByAuthorIdAndProviderId(2L, 1L)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Review existing = new Review();
        existing.setRating(2);
        when(reviewRepository.findByProviderIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(existing, ratedReview(4)));

        reviewService.postReview(provider, author, form);

        ArgumentCaptor<Provider> providerCaptor = ArgumentCaptor.forClass(Provider.class);
        org.mockito.Mockito.verify(providerRepository).save(providerCaptor.capture());

        assertThat(providerCaptor.getValue().getRatingCount()).isEqualTo(2);
        assertThat(providerCaptor.getValue().getAvgRating()).isEqualTo(new BigDecimal("3.00"));
    }

    private static Review ratedReview(int rating) {
        Review review = new Review();
        review.setRating(rating);
        return review;
    }
}
