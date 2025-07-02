package com.example.usersecurity.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {
    ADMIN("관리자"), OPERATOR("운영자"), USER("사용자");

    private final String description;
}
