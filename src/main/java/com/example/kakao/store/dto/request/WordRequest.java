package com.example.kakao.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WordRequest {
    private String st1;
    private String st2;

    public void rearranging () {
        if (isKoreanOnly(st1)) {
            // st1이 한글로만 이루어져 있으므로 그대로 둔다.
            if (!isKoreanOnly(st2)) {
                // st2가 한국어가 아니므로 위치를 바꿀 필요가 없다.
                return;
            }
        } else if (isKoreanOnly(st2)) {
            // st2가 한글로만 이루어져 있다면 st1과 st2를 교체한다.
            String temp = st1;
            st1 = st2;
            st2 = temp;
        }
    }

    private boolean isKoreanOnly(String input) {
        // 한글이 아닌 문자가 발견되면 false 반환
        return input.charAt(0) >= 0xAC00 && input.charAt(0) <= 0xD7A3;

        // 모든 문자가 한글이면 true 반환
    }
}
