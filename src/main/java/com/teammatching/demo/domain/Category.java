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

}
