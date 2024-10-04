package com.springboot.thirdparty.gateway.aadhaarapi.repository;

import com.springboot.thirdparty.gateway.aadhaarapi.model.AadhaarState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Repository
public interface AadhaarStateRepository extends JpaRepository<AadhaarState, Integer> {

    Optional<AadhaarState> findByReferenceId(String referenceId);
}
