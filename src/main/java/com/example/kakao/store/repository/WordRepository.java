package com.example.kakao.store.repository;

import com.example.kakao.store.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {

}
