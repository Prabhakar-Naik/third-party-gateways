package com.springboot.thirdparty.gateway.whatsapp.repository;

import com.springboot.thirdparty.gateway.whatsapp.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author prabhakar, @Date 30-09-2024
 */
public interface CustomersRepository extends JpaRepository<Customers, Integer> {

    Customers findByEmailAndOtp(String email, String otp);

}
