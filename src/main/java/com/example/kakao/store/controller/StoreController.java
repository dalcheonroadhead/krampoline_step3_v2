package com.example.kakao.store.controller;

import com.example.kakao.store.dto.request.WordRequest;
import com.example.kakao.store.dto.response.WordResponse;
import com.example.kakao.store.service.WordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final WordService wordService;

    @PostMapping("/language")
    public ResponseEntity<String> storeWord (@RequestBody WordRequest request){
        return ResponseEntity.ok(wordService.storeWord(request));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<WordResponse>> findAll() {
        return ResponseEntity.ok(wordService.findWords());
    }
}
