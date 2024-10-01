package com.springboot.thirdparty.gateway.whatsapp.service;

import com.springboot.thirdparty.gateway.whatsapp.dto.WhatsappParameters;
import com.springboot.thirdparty.gateway.whatsapp.dto.WhatsappRequest;
import com.springboot.thirdparty.gateway.whatsapp.dto.WhatsappResponse;
import com.springboot.thirdparty.gateway.whatsapp.model.Customers;
import com.springboot.thirdparty.gateway.whatsapp.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author prabhakar, @Date 30-09-2024
 */
@Service
public class CustomerService {

    @Value("${whatsapp.otp.template.name}")
    private String whatsappOtpTemplateName;

    @Value("${whatsapp.send.template.msg.url}")
    private String whatsappTemplateMsgApiUrl;

    @Value("${whatsapp.token}")
    private String token;

    @Autowired
    private CustomersRepository customersRepository;


    public boolean saveCustomer(Customers customer){
        String otp = generateOTP();
        customer.setOtp(otp);
        customer.setOtpStatus("NOT-VERIFIED");
        customersRepository.save(customer);

        WhatsappResponse watResponse = sendOtp(customer.getPhone(),otp);
        System.out.println(watResponse);

        return true;
    }

    public WhatsappResponse sendOtp(String phone, String otp){
        RestTemplate rt = new RestTemplate();
        String apiUrl = whatsappTemplateMsgApiUrl + "?whatsappNumber=" + phone;

        WhatsappParameters params = new WhatsappParameters();
        params.setName("otp");
        params.setValue(otp);

        WhatsappRequest requestDTO = new WhatsappRequest();
        requestDTO.setTemplate_name(whatsappOtpTemplateName);
        requestDTO.setBroadcast_name(whatsappOtpTemplateName);
        requestDTO.setParameters(List.of(params));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        HttpEntity<WhatsappRequest> requestEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<WhatsappResponse> response = rt.postForEntity(apiUrl, requestEntity, WhatsappResponse.class);
        Objects.requireNonNull(response.getBody()).setOtp(otp);
        return response.getBody();
    }



    private String generateOTP() {
        // Length of the OTP
        int length = 4;
        // Generate random digits
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return otp.toString();
    }

    public Customers validateOtp(String email, String otp){
        Customers user = customersRepository.findByEmailAndOtp(email, otp);
        if(user!=null){
            user.setOtpStatus("VERIFIED");
            return customersRepository.save(user);
        }
        return null;
    }




}
