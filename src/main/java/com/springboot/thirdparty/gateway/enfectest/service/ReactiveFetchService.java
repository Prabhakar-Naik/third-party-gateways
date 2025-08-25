package com.springboot.thirdparty.gateway.enfectest.service;

import com.springboot.thirdparty.gateway.enfectest.dto.DataDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.PostDTO;
import com.springboot.thirdparty.gateway.enfectest.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author prabhakar, @Date 15-07-2025
 */
@Service
public class ReactiveFetchService {

    private final WebClient webClient; // or inject via builder

    public ReactiveFetchService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<DataDTO> getData() {
        Mono<List<UserDTO>> users = fetchAllUsers();
        Map<Integer, UserDTO> usersById = new HashMap<>();
        assert users != null;
        for (UserDTO user : Objects.requireNonNull(users.block()).stream().toList()) {
            usersById.put(user.getId(), user);
        }
        Mono<List<PostDTO>> posts = fetchAllPosts();

        return Objects.requireNonNull(posts.block()).stream()
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

    public Mono<List<UserDTO>> fetchAllUsers() {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/users")
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList();
    }

    public Mono<List<PostDTO>> fetchAllPosts() {
        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .retrieve()
                .bodyToFlux(PostDTO.class)
                .collectList();
    }


}
