package com.example.kakao.chatbot.controller;

import com.example.kakao.chatbot.dto.request.UserRequest;
import com.example.kakao.chatbot.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat-bot")
public class ChatBotController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/talk")
    public ResponseEntity<String> letsTalk(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(chatGPTService.Prompt(userRequest.getQuestion(),userRequest.getLang_code()));
    }

    @PostMapping("/teaching")
    public ResponseEntity<String> teachGPT(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(chatGPTService.teaching(userRequest.getQuestion()));
    }


}
