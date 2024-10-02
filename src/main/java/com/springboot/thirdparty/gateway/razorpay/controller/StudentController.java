package com.springboot.thirdparty.gateway.razorpay.controller;

import com.razorpay.RazorpayException;
import com.springboot.thirdparty.gateway.razorpay.entity.StudentOrders;
import com.springboot.thirdparty.gateway.razorpay.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author prabhakar, @Date 27-09-2024
 */
@Controller
@RequestMapping(value = "/api/v1/razorpay/payments")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/")
    public String init() {
        return "index";
    }

    @PostMapping(value = "/create-order", produces = "application/json")
    @ResponseBody
    public ResponseEntity<StudentOrders> createOrder(@RequestBody StudentOrders orders) throws RazorpayException {
        StudentOrders createdOrder = this.studentService.createOrder(orders);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }


    // razor pay must call this method
    @PostMapping(value = "/handle-payment-callback")
    public String handlePaymentCallback(@RequestParam Map<String, String> responsePayLoad){
        this.studentService.updateOrderStatus(responsePayLoad);
        return "success";
    }




}
