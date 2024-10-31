package com.example.kakao.chatbot.service;

import com.example.kakao.chatbot.config.ChatGPTConfig;
import com.example.kakao.chatbot.dto.request.ChatRequest;
import com.example.kakao.chatbot.dto.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String url;

    public String Prompt(String prompt) {
        // 1. 헤더 만들기
        HttpHeaders headers = chatGPTConfig.httpHeaders();
        // 2. request 만들기
        ChatRequest chatRequest = new ChatRequest(model, prompt);
        // 3. 통신을 위한 restTemplate 만들기
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(chatRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ChatResponse response = restTemplate.postForObject(url, requestEntity, ChatResponse.class);

        if(response == null || response.getChoices() == null || response.getChoices().isEmpty()){
            log.error("응답이 비었음");
            throw new RuntimeException();
        }
        return  response.getChoices().get(0).getMessage().getContent();

    }
}
