package com.hoon.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private String name;
    private String token;

    @Builder
    public UserDto(String name, String token){
        this.name = name;
        this.token = token;
    }
}
