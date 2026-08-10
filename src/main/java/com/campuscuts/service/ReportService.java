package com.campuscuts.service;

import com.campuscuts.dto.ReportForm;
import com.campuscuts.entity.Report;
import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.ReportTargetType;
import com.campuscuts.repository.MessageRepository;
import com.campuscuts.repository.ProviderRepository;
import com.campuscuts.repository.ReportRepository;
import com.campuscuts.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;
    private final MessageRepository messageRepository;
    private final ProviderRepository providerRepository;

    public ReportService(ReportRepository reportRepository, ReviewRepository reviewRepository,
                          MessageRepository messageRepository, ProviderRepository providerRepository) {
        this.reportRepository = reportRepository;
        this.reviewRepository = reviewRepository;
        this.messageRepository = messageRepository;
        this.providerRepository = providerRepository;
    }

    @Transactional
    public Report fileReport(User reporter, ReportForm form) {
        Report report = new Report();
        report.setReporter(reporter);
        report.setTargetType(form.getTargetType());
        report.setTargetId(form.getTargetId());
        report.setReason(form.getReason());
        return reportRepository.save(report);
    }

    /** Hydrates the actual reported object — resolved manually since the target has no FK/JPA relationship. */
    public Object resolveTarget(Report report) {
        ReportTargetType type = report.getTargetType();
        Long id = report.getTargetId();
        return switch (type) {
            case REVIEW -> reviewRepository.findById(id).orElse(null);
            case MESSAGE -> messageRepository.findById(id).orElse(null);
            case PROVIDER -> providerRepository.findById(id).orElse(null);
        };
    }
}
