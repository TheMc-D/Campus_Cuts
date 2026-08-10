package com.campuscuts.dto;

import com.campuscuts.entity.enums.ServiceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProviderApplyForm {

    @NotBlank
    private String displayName;

    private String bio;

    @NotNull
    private ServiceType serviceType;

    @NotNull
    private Long campusAreaId;

    private String locationDetail;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Long getCampusAreaId() {
        return campusAreaId;
    }

    public void setCampusAreaId(Long campusAreaId) {
        this.campusAreaId = campusAreaId;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }
}
