package com.example.Preproject.service.API;

import com.example.Preproject.dto.DuckDTO;
import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.Duck;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.DuckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DuckServiceImpl implements DuckService {

    @Autowired
    private DuckRepository duckRepository;

    private final static String URL_DUCK_API = "https://random-d.uk/api/v2";
    private final static String URL_FOR_RANDOM_DUCK = "/random";

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public DuckDTO findDuckURLFromDB(User user) {
        Duck duck = duckRepository.findDuckById(user.getId());
        return new DuckDTO(duck.getId(), duck.getPictureUrl());
    }

    @Override
    public String getDuckURL() {
        String uri = URL_DUCK_API + URL_FOR_RANDOM_DUCK;
        return Objects.requireNonNull(restTemplate.exchange(uri, HttpMethod.GET, getHeaders(), DuckDTO.class)
                        .getBody()).getUrl();
    }

    @Override
    public DuckDTO getDuck() {
        String uri = URL_DUCK_API + URL_FOR_RANDOM_DUCK;
        return restTemplate.exchange(uri, HttpMethod.GET, getHeaders(), DuckDTO.class).getBody();
    }

    @Override
    public void saveDuck(User user) {
        String url = URL_DUCK_API + URL_FOR_RANDOM_DUCK;
        DuckDTO duckDTO = restTemplate.exchange(url, HttpMethod.GET, getHeaders(), DuckDTO.class).getBody();
        duckDTO.setId(user.getId());

        Duck duck = new Duck();
        duck.setPictureUrl(duckDTO.getUrl());
        duck.setUser(user);
        duckRepository.save(duck);
    }

    @Override
    public List<DuckDTO> getAllDuck() {
        return duckRepository.findAll()
                .stream()
                .map(duck -> new DuckDTO(duck.getId(), duck.getPictureUrl()))
                .collect(Collectors.toList());
    }

    private HttpEntity<String> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Application");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

}
