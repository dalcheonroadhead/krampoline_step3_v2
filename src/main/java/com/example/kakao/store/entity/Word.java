package com.example.kakao.store.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String front;
    String back;

    @Builder
    private Word(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public static Word of (String front, String back) {
        return Word.builder()
            .front(front)
            .back(back)
            .build();
    }
}
