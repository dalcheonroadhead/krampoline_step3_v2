package com.example.kakao.chatbot.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class ChatGPTConfig {

    @Value("${openai.api.key}")
    private String secretKey;


    @Bean
    public RestTemplate restTemplate(){
        // 프록시 설정
        HttpHost proxy = new HttpHost("krmp-proxy.9rum.cc",3128,"http");
        // 프록시를 사용한 HttpClient 생성
        CloseableHttpClient httpClient = HttpClients.custom()
            .setProxy(proxy)
            .build();
        // HttpClient를 사용하여 RestTemplate의 요청 팩토리 설정
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }

    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
