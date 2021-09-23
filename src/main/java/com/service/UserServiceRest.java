package com.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserServiceRest implements UserService {

    private final RestTemplate restTemplate;
    private final String serverUrl;
    private HttpHeaders headers;

    public UserServiceRest(RestTemplate restTemplate, @Value("${application.server.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
        this.headers = new HttpHeaders();
    }

    @Override
    public void setConnection() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(serverUrl, String.class);
        String q = forEntity.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";"));

        headers.set("Cookie", q.split(";")[0]);
    }

    @Override
    public String getAllUsers() {
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class).getBody();
    }

    @Override
    public String addUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("name", "Anton");
        map.put("lastName", "Shirshov");
        map.put("age", 24);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, entity, String.class);
        return response.getBody();
    }
    @Override
    public String editUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("name", "Vlad");
        map.put("lastName", "Vladov");
        map.put("age", 24);

        Map<String, String> param = new HashMap<String, String>();
        param.put("id","3");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.PUT, entity, String.class, param);
        return response.getBody();
    }
    @Override
    public String deleteUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("name", "Vadim");
        map.put("lastName", "Vadimov");
        map.put("age", 24);

        Map<String, String> param = new HashMap<String, String>();
        param.put("id","3");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(serverUrl+"/3", HttpMethod.DELETE, entity, String.class, param);
        return response.getBody();
    }
}
