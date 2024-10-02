package com.springboot.thirdparty.gateway.razorpay.repository;

import com.springboot.thirdparty.gateway.razorpay.entity.StudentOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author prabhakar, @Date 27-09-2024
 */
@Repository
public interface StudentOrdersRepository extends JpaRepository<StudentOrders, Integer> {

    StudentOrders findByRazorpayOrderId(String razorpayOrderId);
}
