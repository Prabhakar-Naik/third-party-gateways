package com.springboot.thirdparty.gateway.razorpay.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author prabhakar, @Date 27-09-2024
 */
@Setter
@Getter
@ToString
@Data
@Entity
@Table(name = "student_orders")
public class StudentOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private String name;
    private String email;
    private String phone;
    private String course;
    private Integer amount;
    private String orderStatus;
    private String razorpayOrderId;
}
