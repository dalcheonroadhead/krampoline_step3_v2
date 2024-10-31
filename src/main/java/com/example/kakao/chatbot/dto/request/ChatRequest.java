package com.example.kakao.chatbot.dto.request;

import com.example.kakao.chatbot.dto.ChatMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChatRequest {

    private String model;
    private List<ChatMessage> messages;
    private int n;
    private int max_tokens;

    public ChatRequest(String model,List<ChatMessage> messages ) {
        this.model = model;
        this.messages = messages;
        this.n = 1;
        this.max_tokens = 100;
    }


}
