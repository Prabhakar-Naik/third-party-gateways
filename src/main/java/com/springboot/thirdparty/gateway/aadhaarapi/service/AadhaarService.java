package com.springboot.thirdparty.gateway.aadhaarapi.service;

import com.springboot.thirdparty.gateway.aadhaarapi.config.SandBoxConfig;
import com.springboot.thirdparty.gateway.aadhaarapi.dto.AadhaarRequestDto;
import com.springboot.thirdparty.gateway.aadhaarapi.dto.AadhaarResponseDto;
import com.springboot.thirdparty.gateway.aadhaarapi.dto.OtpRequestDto;
import com.springboot.thirdparty.gateway.aadhaarapi.model.AadhaarState;
import com.springboot.thirdparty.gateway.aadhaarapi.repository.AadhaarStateRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Service
public class AadhaarService {
    private final AadhaarStateRepository stateRepository;
    private final RestTemplate restTemplate;
    private final AuthService authService;

    private final SandBoxConfig sandBoxConfig;

    public AadhaarService(AadhaarStateRepository stateRepository, RestTemplate restTemplate, AuthService authService, SandBoxConfig sandBoxConfig) {
        this.stateRepository = stateRepository;
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.sandBoxConfig = sandBoxConfig;
    }


    public ResponseEntity<AadhaarResponseDto> generateOtpForAadhaar(String aadhaarNumber) {
        String url = this.sandBoxConfig.getSandboxUrl() + "requested url";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",this.authService.getAccessToken());
        headers.set("content-type", "application/json");
        headers.set("accept", "application/json");
        headers.set("x-api-version", this.sandBoxConfig.getVersion());
        headers.set("x-api-key",this.sandBoxConfig.getApiKey());


        AadhaarRequestDto requestBody = new AadhaarRequestDto();
        requestBody.setEntity("requested entity value");
        requestBody.setAadhaarNumber(aadhaarNumber);
        requestBody.setConsent("y");
        requestBody.setReason("For KYC");

        HttpEntity<AadhaarRequestDto> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<AadhaarResponseDto> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, AadhaarResponseDto.class);

        if (Objects.requireNonNull(response.getBody()).getCode() == 200){
            AadhaarState state = new AadhaarState();
            if (this.stateRepository.findByReferenceId(response.getBody().getData().getReferenceId()).isPresent()){

            }

            state.setAadhaarNumber(aadhaarNumber);
            state.setReferenceId(response.getBody().getData().getReferenceId());
            state.setStatus("NOT VERIFIED");
            this.stateRepository.save(state);
        }
        //ResponseEntity<ResponseDto> response = this.restTemplate.getForEntity(sandBoxConfig.getSandboxUrl(), ResponseDto.class);

        return ResponseEntity.ok(response.getBody());
    }


    public ResponseEntity<AadhaarResponseDto> verifyOtp(OtpRequestDto requestDto) {
        String url = this.sandBoxConfig.getSandboxUrl() + "requested url";

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization",this.authService.getAccessToken());
        headers.set("content-type", "application/json");
        headers.set("accept", "application/json");
        headers.set("x-api-version", this.sandBoxConfig.getVersion());
        headers.set("x-api-key",this.sandBoxConfig.getApiKey());


        OtpRequestDto requestBody = new OtpRequestDto();
        requestBody.setEntity("requested entity value");
        requestBody.setReference_id(requestDto.getReference_id());
        requestBody.setOtp(requestDto.getOtp());

        HttpEntity<OtpRequestDto> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<AadhaarResponseDto> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, AadhaarResponseDto.class);


        if (Objects.requireNonNull(response.getBody()).getCode() == 200){
            Optional<AadhaarState> state = this.stateRepository.findByReferenceId(response.getBody().getData().getReferenceId());
            if (state.isPresent()) {
                state.get().setStatus("VERIFIED");
                this.stateRepository.save(state.get());
            }
        }
        return ResponseEntity.ok(response.getBody());
    }



}
