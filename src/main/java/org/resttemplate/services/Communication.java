package org.resttemplate.services;

import org.resttemplate.entity.User;
import org.resttemplate.exceptions.ListHeaderEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.httpHeaders.set("Cookie",
                String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate
                .exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });
        return responseEntity.getBody();
    }

    public String updateUser(User user) {
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
        System.out.println("Вторая часть кода: " + response.getBody());
        return response.getBody();
    }

    public String saveUser(User user) {
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, requestEntity, String.class);
        System.out.println("Первая часть кода: " + response.getBody());
        return response.getBody();
    }

    public String delete(User user) {
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(URL + "/" + user.getId().toString(), HttpMethod.DELETE, request, String.class);
        System.out.println("Третья часть кода: " + response.getBody());
        return response.getBody();
    }
}
