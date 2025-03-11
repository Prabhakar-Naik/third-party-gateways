package com.springboot.thirdparty.gateway.smscountryapi.repository;

import com.springboot.thirdparty.gateway.smscountryapi.model.SmsCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author prabhakar, @Date 11-03-2025
 */
@Repository
public interface SmsCountryRepository  extends JpaRepository<SmsCountry, Integer> {

}
