package com.example.justJwt.jwt.domain;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Data
@Builder
@Setter
@Getter
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long num;

    private String id;

    private String password; // 비밀번호
    private String email; // 이메일
    private String nickname; // 닉네임

    private String accessToken;



    public String getEmail() {
        return email != null ? email : "정보없음";
    }

    public String getId() {
        return id != null ? id : "정보없음";
    }

    public String getNickname() {
        return nickname != null ? nickname : "정보없음";
    }

    public String getPassword() {
        return password != null ? password : "정보없음";
    }


    public void setAccessToken(String accessToken){this.accessToken = accessToken;}

    public void setEmail(String email){this.email=email;}

    public void setNickname(String nickname){this.nickname=nickname;}

}
