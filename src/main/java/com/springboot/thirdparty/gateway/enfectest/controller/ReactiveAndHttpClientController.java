package com.springboot.thirdparty.gateway.enfectest.controller;

import com.springboot.thirdparty.gateway.enfectest.dto.DataDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.PostDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.UserDTO;
import com.springboot.thirdparty.gateway.enfectest.service.Java11HttpClientService;
import com.springboot.thirdparty.gateway.enfectest.service.ReactiveFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author prabhakar, @Date 15-07-2025
 */

@RestController
@RequestMapping(value = "/other-ways")
public class ReactiveAndHttpClientController {


    private final ReactiveFetchService fetchService;

    private final Java11HttpClientService httpClientService;

    public ReactiveAndHttpClientController(ReactiveFetchService fetchService, Java11HttpClientService httpClientService) {
        this.fetchService = fetchService;
        this.httpClientService = httpClientService;
    }

    @GetMapping("/fetchAllUsersAsList")
    public Mono<List<UserDTO>> fetchAllUsersAsList(){
        return fetchService.fetchAllUsers();
    }

    @GetMapping("/fetchAllPostsAsList")
    public Mono<List<PostDTO>> fetchAllPostsAsList(){
        return fetchService.fetchAllPosts();
    }

    @GetMapping("/getCombineData")
    public List<DataDTO> getCombineData(){
        return this.fetchService.getData();
    }

    // http client

    @GetMapping("/fetchAllUsersAsListByHttpClient")
    public List<UserDTO> fetchAllUsersAsListByHttpClient(){
        try {
            return httpClientService.fetchAllUsers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/fetchAllPostsAsListByHttpClient")
    public List<PostDTO> fetchAllPostsAsListByHttpClient() throws Exception {
        return httpClientService.fetchAllPosts();
    }

    @GetMapping("/getCombineDataByHttpClient")
    public List<DataDTO> getCombineDataByHttpClient(){
        return this.fetchService.getData();
    }

}
