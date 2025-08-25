package com.springboot.thirdparty.gateway.enfectest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.thirdparty.gateway.enfectest.dto.DataDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.PostDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author prabhakar, @Date 15-07-2025
 */
@Service
public class Java11HttpClientService {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();


    public List<DataDTO> getData() throws Exception {
        List<UserDTO> users = fetchAllUsers();

        Map<Integer, UserDTO> usersById = new HashMap<>();
        assert users != null;
        for (UserDTO user : users) {
            usersById.put(user.getId(), user);
        }
        List<PostDTO> posts = fetchAllPosts();

        return posts.stream()
                //.filter(p -> p.getId() == 1)
                .map(post -> {
                    UserDTO user = usersById.get(post.getUserId());
                    if (user == null) return null;

                    DataDTO dto = new DataDTO();
                    dto.setId(post.getId());

                    // copy geo
                    DataDTO.Geo g = new DataDTO.Geo();
                    g.setLat(user.getAddress().getGeo().getLat());
                    g.setLng(user.getAddress().getGeo().getLng());
                    dto.setGeo(g);

                    // copy company name
                    DataDTO.Company c = new DataDTO.Company();
                    c.setName(user.getCompany().getName());
                    dto.setCompany(c);

                    dto.setTitle(post.getTitle());
                    return dto;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<UserDTO> fetchAllUsers() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI("https://jsonplaceholder.typicode.com/users"))
                .GET()
                .build();

        String json = http.send(req, HttpResponse.BodyHandlers.ofString()).body();
        return mapper.readValue(json, new TypeReference<>() {});
    }

    public List<PostDTO> fetchAllPosts() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI("https://jsonplaceholder.typicode.com/posts"))
                .GET()
                .build();

        String json = http.send(req, HttpResponse.BodyHandlers.ofString()).body();
        return mapper.readValue(json, new TypeReference<>() {});
    }
}
