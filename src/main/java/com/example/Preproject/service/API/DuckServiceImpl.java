package com.example.Preproject.service.API;

import com.example.Preproject.config.properties.ApiConfigProperties;
import com.example.Preproject.config.properties.DuckApiConfigProperties;
import com.example.Preproject.dto.DuckDTO;
import com.example.Preproject.model.Duck;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.DuckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DuckServiceImpl implements DuckService {

    @Autowired
    private DuckRepository duckRepository;
    @Autowired
    private ApiConfigProperties apiConfigProperties;
    @Autowired
    private DuckApiConfigProperties duckApiConfigProperties;

    @Override
    public DuckDTO findDuckURLFromDB(User user) {
        Duck duck = duckRepository.findDuckById(user.getId());
        return new DuckDTO(duck.getId(), duck.getPictureUrl());
    }

    @Override
    public DuckDTO getDuck() {
        String uri = duckApiConfigProperties.getURL_DUCK_API() + duckApiConfigProperties.getURL_FOR_RANDOM_DUCK();
        return apiConfigProperties.getRestTemplate()
                .exchange(uri, HttpMethod.GET, getHeaders(), DuckDTO.class).getBody();
    }

    @Override
    public void saveDuck(User user) {
        if (duckRepository.findDuckById(user.getId()) == null) {
            String url = duckApiConfigProperties.getURL_DUCK_API() + duckApiConfigProperties.getURL_FOR_RANDOM_DUCK();
            DuckDTO duckDTO = apiConfigProperties.getRestTemplate()
                    .exchange(url, HttpMethod.GET, getHeaders(), DuckDTO.class).getBody();
            if (duckDTO != null) {
                duckDTO.setId(user.getId());
                Duck duck = new Duck();
                duck.setPictureUrl(duckDTO.getUrl());
                duck.setUser(user);
                duckRepository.save(duck);
            }
        }
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
        return new HttpEntity<>(headers);
    }

}
