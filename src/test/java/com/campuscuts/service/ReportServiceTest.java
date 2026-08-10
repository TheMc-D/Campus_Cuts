package com.campuscuts.service;

import com.campuscuts.dto.ReportForm;
import com.campuscuts.entity.Report;
import com.campuscuts.entity.Review;
import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.ReportTargetType;
import com.campuscuts.repository.MessageRepository;
import com.campuscuts.repository.ProviderRepository;
import com.campuscuts.repository.ReportRepository;
import com.campuscuts.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void resolveTarget_ofTypeReview_looksUpFromReviewRepository() {
        Review review = new Review();
        when(reviewRepository.findById(42L)).thenReturn(Optional.of(review));

        Report report = new Report();
        report.setTargetType(ReportTargetType.REVIEW);
        report.setTargetId(42L);

        assertThat(reportService.resolveTarget(report)).isSameAs(review);
    }

    @Test
    void fileReport_savesReportWithReporterAndTarget() {
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User reporter = new User();
        ReportForm form = new ReportForm();
        form.setTargetType(ReportTargetType.MESSAGE);
        form.setTargetId(7L);
        form.setReason("Inappropriate content");

        Report saved = reportService.fileReport(reporter, form);

        assertThat(saved.getReporter()).isSameAs(reporter);
        assertThat(saved.getTargetType()).isEqualTo(ReportTargetType.MESSAGE);
        assertThat(saved.getTargetId()).isEqualTo(7L);
    }
}
