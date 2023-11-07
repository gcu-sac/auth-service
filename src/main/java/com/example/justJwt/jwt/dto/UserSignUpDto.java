package com.example.justJwt.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSignUpDto {
    private String id;
    private String password;

    private String email;

    private String nickname;
}
