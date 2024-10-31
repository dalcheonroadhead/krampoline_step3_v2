package com.example.kakao.chatbot.service;

import com.example.kakao.chatbot.config.ChatGPTConfig;
import com.example.kakao.chatbot.dto.ChatMessage;
import com.example.kakao.chatbot.dto.request.ChatRequest;
import com.example.kakao.chatbot.dto.response.ChatResponse;
import java.util.ArrayList;
import java.util.List;
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

        // 제주도 방언 고정 응답 설정
        switch (prompt) {
            case "안녕하세요":
                return "혼저옵서예";
            case "감사합니다":
                return "고맙수다";
            case "어디 가세요?":
                return "어디 감수과?";
            case "맛있어요":
                return "맛지다";
            case "좋아요":
                return "조으다";
            case "싫어요":
                return "망츠럽다";
            case "잘 지내세요":
                return "잘 지내수다";
            case "무엇을 하고 있나요?":
                return "뭣 하난고예?";
            case "아주 좋아요":
                return "혼저 조으다";
            case "도와주세요":
                return "도와줍서";
            case "재미있어요":
                return "재미지다";
            case "이거 뭐에요?":
                return "이거 뭐라우꽈?";
            case "괜찮아요":
                return "괜챃수다";
            case "피곤해요":
                return "피곤하우다";
            case "졸려요":
                return "졸립수다";
            case "배고파요":
                return "배곱다";
            case "목말라요":
                return "목마름수다";
            case "덥네요":
                return "덥수다";
            case "춥네요":
                return "춥수다";
            case "내일 봐요":
                return "내일 봅서";
            case "기다려주세요":
                return "기다려줍서";
            case "오랜만이에요":
                return "오랜만이우다";
            case "행복하세요":
                return "행복하수다";
            case "오늘 날씨 좋네요":
                return "오늘 날씨 조우다";
            case "천천히 하세요":
                return "느릿느릿 하수다";
            case "빠르게 해주세요":
                return "빨리 하수다";
            case "조심하세요":
                return "조심하수다";
            case "즐겁게 지내세요":
                return "즐겁게 지내수다";
            case "사랑해요":
                return "사랑하우다";
            case "축하합니다":
                return "축하드립수다";
            case "힘내세요":
                return "힘내수다";
            case "잘 하세요":
                return "잘 하수다";
            case "여기 앉으세요":
                return "여기 앉아줍서";
            case "어떻게 지내세요?":
                return "어떵 지내우꽈?";
            case "잘 했어요":
                return "잘 헸수다";
            case "배부르네요":
                return "배불름수다";
            case "시작해요":
                return "시작합서";
            case "끝났어요":
                return "끝났수다";
            case "어디에 있나요?":
                return "어디 있수과?";
            case "어서 오세요":
                return "혼저 옵서";
            case "가세요":
                return "가줍서";
            case "이해했어요":
                return "이해햄수다";
            case "알겠어요":
                return "알아들었수다";
            case "이름이 뭐에요?":
                return "이름이 뭐우꽈?";
            case "몇 시에요?":
                return "몇 시우꽈?";
            case "화이팅":
                return "홧팅하수다";
            case "고맙습니다":
                return "감사하우다";
            case "내일 만나요":
                return "내일 만납서";
            case "잘 자요":
                return "잘 자우다";
            case "꿈 꾸세요":
                return "꿈 꾸수다";
            default:
                // 지정된 고정 응답 외의 경우, 기존 로직 수행
                break;
        }


        // 대화 메시지 리스트 생성
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", "You are a translation expert that processes inputs based on a specified language code and translates them accordingly. Your main task is as follows:- If the input includes `lang_code: ko`, first translate the given text into Jeju dialect. Respond only with the Jeju dialect translation. - If the input includes `lang_code: cn`, translate the given text into Chinese. Respond with the Chinese translation. - If the input includes `lang_code: ne`, translate the given text into Nepali. Respond with the Nepali translation."));

        // 언어 코드에 따라 프롬프트를 적절히 설정
        switch (language) {
            case "ko":
                messages.add(new ChatMessage("user", " lang_code: ko, word: "+ prompt ));
                break;
            case "cn":
                messages.add(new ChatMessage("user", " lang_code: china, word: "+ prompt));
                break;
            case "en":
                messages.add(new ChatMessage("user", " lang_code: english, word: "+ prompt));
                break;
            case "ne":
                messages.add(new ChatMessage("user", " lang_code: Nepail, word: " + prompt));
                break;
            default:
                // 기본적으로 입력된 언어 그대로 처리
                messages.add(new ChatMessage("user", prompt));
                break;
        }

        // 2. 요청 객체 생성
        ChatRequest chatRequest = new ChatRequest(model, messages);
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(chatRequest, headers);

        // 3. 프록시가 설정된 RestTemplate 사용
        RestTemplate restTemplate =  chatGPTConfig.restTemplate();

        ChatResponse response = restTemplate.postForObject(url, requestEntity, ChatResponse.class);

        // 4. 응답 처리
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            log.error("응답이 비었음");
            throw new RuntimeException("OpenAI 응답이 비었거나 유효하지 않습니다.");
        }
        return response.getChoices().get(0).getMessage().getContent();
    }

    public String teaching(String prompt) {

        return "deprecated 안씀!";
    }
}
