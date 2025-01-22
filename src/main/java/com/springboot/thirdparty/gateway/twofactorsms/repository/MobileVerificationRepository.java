package com.springboot.thirdparty.gateway.twofactorsms.repository;

import com.springboot.thirdparty.gateway.twofactorsms.model.MobileVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MobileVerificationRepository extends JpaRepository<MobileVerification, Integer> {

    Optional<MobileVerification> findByProcessId(String processId);
}
