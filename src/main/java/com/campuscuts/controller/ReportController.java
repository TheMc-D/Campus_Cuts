package com.campuscuts.controller;

import com.campuscuts.dto.ReportForm;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.ReportService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    public String fileReport(@ModelAttribute ReportForm form, @AuthenticationPrincipal AppUserPrincipal principal) {
        reportService.fileReport(principal.getUser(), form);
        String returnTo = form.getReturnTo();
        boolean isSafeRelativePath = StringUtils.hasText(returnTo) && returnTo.startsWith("/") && !returnTo.startsWith("//");
        return "redirect:" + (isSafeRelativePath ? returnTo : "/");
    }
}
