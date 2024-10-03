package com.springboot.thirdparty.gateway.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.thirdparty.gateway.paypal.service.PayPalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Slf4j
@Controller
@RequestMapping(value = "/api/v1/paypal")
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @GetMapping("/")
    public String home(){
        return "paypalIndex";
    }

    final String cancelUrl = "http://localhost:8080/api/v1/paypal/payment/cancel";
    final String successUrl = "http://localhost:8080/api/v1/paypal/payment/success";
    @PostMapping(value = "/payment/create")
    public RedirectView createPayment(){
        try{

            Payment payment = this.payPalService.createPayment(
                    10.0,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment Description",
                    cancelUrl,
                    successUrl);

            for (Links links: payment.getLinks()){
                if (links.getRel().equals("approval_utl")){
                    return new RedirectView(links.getHref());
                }
            }

            return null;
        }catch (PayPalRESTException e){
            log.error("Error Occurred:: ",e);
        }
        return new RedirectView(cancelUrl);
    }


    @GetMapping(value = "/payment/success")
    public String paymentSuccess(@RequestParam String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = payPalService.executePayment(paymentId,payerId);
            if (payment.getState().equals("approved")){
                return "paymentSuccess";
            }
        }catch (PayPalRESTException e){
            log.error("Error Occurred:: ",e);
        }
        return "paymentSuccess";
    }

    @GetMapping(value = "/payment/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }


    @GetMapping(value = "/payment/error")
    public String paymentError(){
        return "paymentError";
    }


}
