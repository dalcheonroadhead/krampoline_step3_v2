package com.example.kakao.chatbot.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class ChatGPTConfig {

    @Value("${openai.api.key}")
    private String secretKey;


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
