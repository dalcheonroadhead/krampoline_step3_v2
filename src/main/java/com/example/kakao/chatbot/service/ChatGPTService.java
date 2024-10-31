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

    public String Prompt(String prompt, String language) {
        // 1. 헤더 만들기
        HttpHeaders headers = chatGPTConfig.httpHeaders();  // 필요한 헤더 구성 (e.g. Authorization)

        // 언어 코드에 따른 프롬프트 변경 로직 추가
        switch (language) {
            case "ko":
                prompt = "Translate this text to Korean and then convert it to Jeju dialect: " + prompt;
                break;
            case "cn":
                prompt = "Translate this text to Chinese: " + prompt;
                break;
            case "ne":
                prompt = "Translate this text to Nepali: " + prompt;
                break;
            default:
                log.error("지원하지 않는 언어 코드: " + language);
                throw new RuntimeException("지원하지 않는 언어 코드입니다.");
        }

        // 2. 요청 객체 생성
        ChatRequest chatRequest = new ChatRequest(model, prompt, "user");
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(chatRequest, headers);

        // 3. 프록시가 설정된 RestTemplate 사용
        RestTemplate restTemplate = chatGPTConfig.restTemplate();

        ChatResponse response = restTemplate.postForObject(url, requestEntity, ChatResponse.class);

        // 4. 응답 처리
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            log.error("응답이 비었음");
            throw new RuntimeException("OpenAI 응답이 비었거나 유효하지 않습니다.");
        }
        return response.getChoices().get(0).getMessage().getContent();

    }

    public String teaching(String prompt) {
        // 1. 헤더 만들기
        HttpHeaders headers = chatGPTConfig.httpHeaders();  // 필요한 헤더 구성 (e.g. Authorization)

        // 2. 요청 객체 생성
        ChatRequest chatRequest = new ChatRequest(model, prompt, "system");
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(chatRequest, headers);

        // 3. 프록시가 설정된 RestTemplate 사용
        RestTemplate restTemplate = chatGPTConfig.restTemplate();

        ChatResponse response = restTemplate.postForObject(url, requestEntity, ChatResponse.class);

        // 4. 응답 처리
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            log.error("응답이 비었음");
            throw new RuntimeException("OpenAI 응답이 비었거나 유효하지 않습니다.");
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
