package com.springboot.thirdparty.gateway.enfectest.controller;

import com.springboot.thirdparty.gateway.enfectest.dto.DataDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.PostDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.UserDTO;
import com.springboot.thirdparty.gateway.enfectest.service.DataAggregationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author prabhakar, @Date 27-06-2025
 */

@RestController
@RequestMapping(value = "/aggregate")
public class DataAggregateController {


    private final DataAggregationService service;

    public DataAggregateController(DataAggregationService service) {
        this.service = service;
    }

    @GetMapping(value = "/getData/{id}")
    public List<DataDTO> getData(@PathVariable Integer id){
        return service.getData(id);
    }

    @GetMapping("/fetchData")
    public List<DataDTO> getData(){
        return service.fetchAndAssemble();
    }

    @GetMapping("/fetchAllUsersAsList")
    public List<UserDTO> fetchAllUsersAsList(){
        return service.fetchAllUsersAsList();
    }

    @GetMapping("/fetchAllPostsAsList")
    public List<PostDTO> fetchAllPostsAsList(){
        return service.fetchAllPostsAsList();
    }


    @GetMapping("/get")
    public List<DataDTO> getDataList(){
        return this.service.getDataDtos();
    }

}
