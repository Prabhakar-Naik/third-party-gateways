package com.springboot.thirdparty.gateway.razorpay.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.thirdparty.gateway.razorpay.entity.StudentOrders;
import com.springboot.thirdparty.gateway.razorpay.repository.StudentOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author prabhakar, @Date 27-09-2024
 */
@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentOrdersRepository studentOrdersRepository;

    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    private RazorpayClient razorpayClient;

    public StudentOrders createOrder(StudentOrders studentOrders) throws RazorpayException {
        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", studentOrders.getAmount() * 100); // amount in the form of paise
        orderReq.put("currency", "INR");
        orderReq.put("receipt", studentOrders.getEmail());

        this.razorpayClient = new RazorpayClient(razorPayKey, razorPaySecret);
        // create order in razor pay
        Order razorPayOrder = razorpayClient.orders.create(orderReq);

        // for clarify
        System.out.println(razorPayOrder.toJson());

        studentOrders.setRazorpayOrderId(razorPayOrder.get("id"));
        studentOrders.setOrderStatus(razorPayOrder.get("status"));
        this.studentOrdersRepository.save(studentOrders);
        return studentOrders;
    }


    public StudentOrders updateOrderStatus(Map<String, String> responsePayLoad) {
        String razorPayOrderId = responsePayLoad.get("razorpay_order_id");

        StudentOrders order = this.studentOrdersRepository.findByRazorpayOrderId(razorPayOrderId);

        order.setOrderStatus("PAYMENT_COMPLETED");
        StudentOrders updatedOrder = this.studentOrdersRepository.save(order);
        return updatedOrder;
    }

}
