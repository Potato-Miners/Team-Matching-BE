package com.teammatching.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    DEVELOPMENT("개발"),
    HOBBY("취미"),
    SPORT("스포츠"),
    GAME("게임");

    private final String key;

    public static Category from(String category){
        return switch (category) {
            case "개발" -> Category.DEVELOPMENT;
            case "취미" -> Category.HOBBY;
            case "스포츠" -> Category.SPORT;
            case "게임" -> Category.GAME;
            default -> throw new RuntimeException("유효하지 않은 카테고리입니다.");
        };
    }
}
