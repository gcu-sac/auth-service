package com.example.justJwt.jwt.service;

import com.example.justJwt.jwt.domain.User;
import com.example.justJwt.jwt.dto.UserSignUpDto;
import com.example.justJwt.jwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

   // private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;



  /*  public User registerUser(UserSignUpDto userSignUpDto) {
        return userRepository.save(userSignUpDto);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }*/
   /* public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .id(userSignUpDto.getId())
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .build();

       // user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }*/
}
