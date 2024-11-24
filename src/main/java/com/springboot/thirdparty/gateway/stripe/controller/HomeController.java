package com.springboot.thirdparty.gateway.stripe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author prabhakar, @Date 24-11-2024
 */
@Controller
public class HomeController {

    @GetMapping
    public String index(){
        return "stripeIndex";
    }

    @GetMapping("/success")
    public String success(){
        return "stripeSuccess";
    }

    @GetMapping("/cancel")
    public String cancel(){
        return "stripeCancel";
    }
}
