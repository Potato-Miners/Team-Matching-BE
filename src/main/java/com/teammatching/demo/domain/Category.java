package com.teammatching.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    DEVELOPMENT("DEVELOPMENT"),
    HOBBY("HOBBY"),
    SPORT("SPORT"),
    GAME("GAME");

    private final String key;

    public static Category from(String category){
        return switch (category) {
            case "DEVELOPMENT" -> Category.DEVELOPMENT;
            case "HOBBY" -> Category.HOBBY;
            case "SPORT" -> Category.SPORT;
            case "GAME" -> Category.GAME;
            default -> throw new RuntimeException("유효하지 않은 카테고리입니다.");
        };
    }
}
