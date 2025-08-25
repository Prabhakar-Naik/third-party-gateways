package com.springboot.thirdparty.gateway.enfectest.service;

import com.springboot.thirdparty.gateway.config.DataAccessConfig;
import com.springboot.thirdparty.gateway.enfectest.dto.DataDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.PostDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author prabhakar, @Date 27-06-2025
 */

@Service
public class DataAggregationService {

    private final RestTemplate restTemplate;

    private final DataAccessConfig config;

    public DataAggregationService(RestTemplate restTemplate, DataAccessConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    // using core logic for loop
    public List<DataDTO> fetchAndAssemble() {
        // 1) Fetch all users
        UserDTO[] usersArray = restTemplate.getForObject(
                this.config.getUsersUrl(),
                /*"https://jsonplaceholder.typicode.com/users",*/
                UserDTO[].class
        );
        // 2) Build lookup map by user ID
        Map<Integer, UserDTO> usersById = new HashMap<>();
        assert usersArray != null;
        for (UserDTO user : usersArray) {
            usersById.put(user.getId(), user);
        }

        // 3) Fetch all posts
        PostDTO[] postsArray = restTemplate.getForObject(
                this.config.getPostsUrl(),
                /*"https://jsonplaceholder.typicode.com/posts",*/
                PostDTO[].class
        );

        // 4) Iterate posts, join with user, map to DataDTO
        List<DataDTO> result = new ArrayList<>();
        assert postsArray != null;
        for (PostDTO post : postsArray) {
            UserDTO user = usersById.get(post.getUserId());
            if (user == null) {
                // no matching user → skip
                continue;
            }

            DataDTO dto = new DataDTO();
            dto.setId(post.getId());

            // copy geo from user.address.geo
            DataDTO.Geo geo = new DataDTO.Geo();
            geo.setLat(user.getAddress().getGeo().getLat());
            geo.setLng(user.getAddress().getGeo().getLng());
            dto.setGeo(geo);

            // copy company name
            DataDTO.Company comp = new DataDTO.Company();
            comp.setName(user.getCompany().getName());
            dto.setCompany(comp);

            dto.setTitle(post.getTitle());

            result.add(dto);
        }

        return result;
    }


    // using streams
    public List<DataDTO> getData(Integer id) {
        // 1) Fetch users
        UserDTO[] users = restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/users",
                UserDTO[].class
        );
        // build a lookup by userId
        assert users != null;
        Map<Integer, UserDTO> usersById = Arrays.stream(users)
                .collect(Collectors.toMap(UserDTO::getId, u -> u));

        // 2) Fetch posts
        PostDTO[] posts = restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/posts",
                PostDTO[].class
        );

        // 3) For each post, if matching user exists, map to your DataDTO
        assert posts != null;
        return Arrays.stream(posts)
                .filter(p -> p.getId() == id)
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


    public List<DataDTO> getDataDtos() {
        List<UserDTO> users = fetchAllUsersAsList();

        Map<Integer, UserDTO> usersById = new HashMap<>();
        assert users != null;
        for (UserDTO user : users) {
            usersById.put(user.getId(), user);
        }

        List<PostDTO> posts = fetchAllPostsAsList();

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


    public List<UserDTO> fetchAllUsersAsList() {
        String url = "https://jsonplaceholder.typicode.com/users";
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    public Set<UserDTO> fetchAllUsersAsSet() {
        List<UserDTO> userList = fetchAllUsersAsList();
        return new HashSet<>(userList);
    }

    public List<PostDTO> fetchAllPostsAsList() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<List<PostDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public Set<PostDTO> fetchAllPostsAsSet() {
        List<PostDTO> postList = fetchAllPostsAsList();
        return new HashSet<>(postList);
    }
}
