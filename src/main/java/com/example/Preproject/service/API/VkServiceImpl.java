package com.example.Preproject.service.API;


import com.example.Preproject.config.properties.ApiConfigProperties;
import com.example.Preproject.config.properties.VkApiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Service
public class VkServiceImpl implements VkService {

    @Autowired
    private ApiConfigProperties apiConfigProperties;
    @Autowired
    private VkApiConfigProperties vkApiConfigProperties;


    public ResponseEntity<String> sendCommentToVK(String message) {

        System.out.println("\nVkProp = " + vkApiConfigProperties.toString() + "\n");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", vkApiConfigProperties.getTokenWithBearer());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("owner_id", vkApiConfigProperties.getOWNER_ID());
        map.add("post_id", vkApiConfigProperties.getPOST_ID());
        map.add("message", message);
        map.add("v", vkApiConfigProperties.getV());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        apiConfigProperties.getRestTemplate()
                .postForEntity(vkApiConfigProperties.getURL_CREATE_COMMENT(), request , String.class);

        return new ResponseEntity<>("Запрос отправен в ВК", HttpStatus.OK);
    }

    public ResponseEntity<String> getCommentsFromVK() {
        String uri = vkApiConfigProperties.getURL_GET_COMMENTS() +
                "?owner_id=" + vkApiConfigProperties.getOWNER_ID() +
                "&post_id=" + vkApiConfigProperties.getPOST_ID() +
                "&preview_length=" + vkApiConfigProperties.getPREVIEW_LENGTH() +
                "&access_token=" + vkApiConfigProperties.getTokenWithoutBearer() +
                "&v=" + vkApiConfigProperties.getV();
        return apiConfigProperties.getRestTemplate().getForEntity(uri, String.class);
    }

    public ResponseEntity<String> editCommentFromVK(String commentId, String message) {
        String uri = vkApiConfigProperties.getURL_UPDATE_COMMENT() +
                "?owner_id=" + vkApiConfigProperties.getOWNER_ID() +
                "&comment_id=" + commentId +
                "&message=" + message +
                "&access_token=" + vkApiConfigProperties.getTokenWithoutBearer() +
                "&v=" + vkApiConfigProperties.getV();
        return apiConfigProperties.getRestTemplate().getForEntity(uri, String.class);
    }

    public ResponseEntity<String> deleteCommentFromVK(String commentId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", vkApiConfigProperties.getTokenWithBearer());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("owner_id", vkApiConfigProperties.getOWNER_ID());
        map.add("comment_id", commentId);
        map.add("preview_length", vkApiConfigProperties.getPREVIEW_LENGTH());
        map.add("v", vkApiConfigProperties.getV());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        apiConfigProperties.getRestTemplate()
                .postForEntity(vkApiConfigProperties.getURL_DELETE_COMMENT(), request , String.class);
        return new ResponseEntity<>("Запрос на удаление отправен в ВК", HttpStatus.OK);
    }
}
