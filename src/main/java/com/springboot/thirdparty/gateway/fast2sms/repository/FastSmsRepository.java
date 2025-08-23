package com.springboot.thirdparty.gateway.fast2sms.repository;

import com.springboot.thirdparty.gateway.fast2sms.model.FastSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author prabhakar, @Date 26-03-2025
 */
@Repository
public interface FastSmsRepository extends JpaRepository<FastSms, Integer> {

    Optional<FastSms> findByRequestId(String requestId);

}
