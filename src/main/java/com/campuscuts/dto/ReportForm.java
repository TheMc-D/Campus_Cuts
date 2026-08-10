package com.campuscuts.dto;

import com.campuscuts.entity.enums.ReportTargetType;
import jakarta.validation.constraints.NotNull;

public class ReportForm {

    @NotNull
    private ReportTargetType targetType;

    @NotNull
    private Long targetId;

    private String reason;

    /** Where to redirect back to after the report is filed. */
    private String returnTo;

    public ReportTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(ReportTargetType targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReturnTo() {
        return returnTo;
    }

    public void setReturnTo(String returnTo) {
        this.returnTo = returnTo;
    }
}
