package com.campuscuts.repository;

import com.campuscuts.entity.Provider;
import com.campuscuts.entity.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Optional<Provider> findByUserId(Long userId);

    List<Provider> findByCampusArea_School_IdAndServiceType(Long schoolId, ServiceType serviceType);

    List<Provider> findByCampusArea_School_Id(Long schoolId);
}
