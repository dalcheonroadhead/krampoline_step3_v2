package com.example.kakao.chatbot.dto.response;


import com.example.kakao.chatbot.dto.ChatMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private List<Choice> choices;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private ChatMessage message;
    }
}
