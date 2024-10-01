package com.springboot.thirdparty.gateway.whatsapp.controller;

import com.springboot.thirdparty.gateway.whatsapp.model.Customers;
import com.springboot.thirdparty.gateway.whatsapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

/**
 * @author prabhakar, @Date 30-09-2024
 */
@Controller
@RequestMapping(value = "/api/v1/whatsapp")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String customerIndex(Model model){
        model.addAttribute("customerObj", new Customers());
        return "customerIndex";
    }

    @PostMapping("/register")
    public String save(Customers customer, Model model){
        boolean isSaved = customerService.saveCustomer(customer);
        customer.setOtp(null);
        model.addAttribute("customerObj", customer);
        return "customerSuccess";
    }

    @PostMapping("/validate")
    public String validateOTP(@ModelAttribute("customerObj") Customers customerObj, Model model){
        Customers u = customerService.validateOtp(customerObj.getEmail(), customerObj.getOtp());
        if(u!=null){
            model.addAttribute("verify_sms", "Congratulations, Your Account Activated..!!");
        }else{
            model.addAttribute("unVerify_sms", "Invalid OTP, Enter Correct OTP..!!");
        }
        return "success";
    }





}
