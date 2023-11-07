package com.example.justJwt.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class GetUserResponseDto {

    private String id;
    private String email; // 이메일
    private String nickname; // 닉네임
}
