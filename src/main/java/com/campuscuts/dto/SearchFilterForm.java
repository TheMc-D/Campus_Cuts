package com.campuscuts.dto;

import com.campuscuts.entity.enums.ServiceType;

public class SearchFilterForm {

    private Long schoolId;

    private ServiceType serviceType;

    private String q;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
