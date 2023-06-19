package com.example.Preproject.service.API;


import com.example.Preproject.config.properties.ApiConfigProperties;
import com.example.Preproject.config.properties.TemplateConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class VkServiceImpl implements VkService {

    private final static String URL_CREATE_COMMENT = "https://api.vk.com/method/wall.createComment";
    private final static String URL_GET_COMMENTS = "https://api.vk.com/method/wall.getComments";
    private final static String URL_UPDATE_COMMENT = "https://api.vk.com/method/wall.editComment";
    private final static String URL_DELETE_COMMENT = "https://api.vk.com/method/wall.deleteComment";

    private final static String OWNER_ID = "-220604044";
    private final static String POST_ID = "2";
    private final static String PREVIEW_LENGTH = "0";
    private final static String V = "5.131";

    @Autowired
    private ApiConfigProperties apiConfigProperties;

    @Autowired
    private TemplateConfigProperties templateConfigProperties;


    public ResponseEntity<String> sendCommentToVK(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", apiConfigProperties.getVkWithBearer());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("owner_id", OWNER_ID);
        map.add("post_id", POST_ID);
        map.add("message", message);
        map.add("v", V);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        restTemplate().postForEntity(URL_CREATE_COMMENT, request , String.class);

        return new ResponseEntity<>("Запрос отправен в ВК", HttpStatus.OK);
    }

    public ResponseEntity<String> getCommentsFromVK() {
        String uri = URL_GET_COMMENTS +
                "?owner_id=" + OWNER_ID +
                "&post_id=" + POST_ID +
                "&preview_length=" + PREVIEW_LENGTH +
                "&access_token=" + apiConfigProperties.getVkWithoutBearer() +
                "&v=" + V;
        return restTemplate().getForEntity(uri, String.class);
    }

    public ResponseEntity<String> editCommentFromVK(String commentId, String message) {
        String uri = URL_UPDATE_COMMENT +
                "?owner_id=" + OWNER_ID +
                "&comment_id=" + commentId +
                "&message=" + message +
                "&access_token=" + apiConfigProperties.getVkWithoutBearer() +
                "&v=" + V;
        return restTemplate().getForEntity(uri, String.class);
    }

    public ResponseEntity<String> deleteCommentFromVK(String commentId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", apiConfigProperties.getVkWithBearer());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("owner_id", OWNER_ID);
        map.add("comment_id", commentId);
        map.add("preview_length", PREVIEW_LENGTH);
        map.add("v", V);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        restTemplate().postForEntity(URL_DELETE_COMMENT, request , String.class);
        return new ResponseEntity<>("Запрос на удаление отправен в ВК", HttpStatus.OK);
    }

    private RestTemplate restTemplate() {
        return templateConfigProperties.getRestTemplate();
    }
}
