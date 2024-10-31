package com.example.kakao.store.service;

import com.example.kakao.store.dto.request.WordRequest;
import com.example.kakao.store.dto.response.WordResponse;
import com.example.kakao.store.entity.Word;
import com.example.kakao.store.repository.WordRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    public String storeWord(WordRequest wordRequest) {
        wordRequest.rearranging();
        try{
            wordRepository.save(Word.of(wordRequest.getSt1(),wordRequest.getSt2()));
        }catch (Exception e){
            throw new RuntimeException("저장 시 에러 남.");
        }

        return "저장 완료";
    }

    public List<WordResponse> findWords(){
        try{
            List<WordResponse> wordResponses = wordRepository.findAll().stream()
                .map(word -> {
                    WordResponse response = new WordResponse();
                    response.setFront(word.getFront());
                    response.setBack(word.getBack());
                    return response;
                })
                .collect(Collectors.toList());
            return wordResponses;
        }catch (Exception e){
            throw new RuntimeException("저장 시 에러 남.");
        }
    }
}
