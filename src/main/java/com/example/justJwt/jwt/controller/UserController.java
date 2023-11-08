package com.example.justJwt.jwt.controller;

import java.util.Map;
import java.util.Optional;


import com.example.justJwt.jwt.domain.User;
import com.example.justJwt.jwt.dto.GetUserResponseDto;
import com.example.justJwt.jwt.dto.UserSignUpDto;
import com.example.justJwt.jwt.repository.UserRepository;
import com.example.justJwt.jwt.service.JwtServiceImpl;
import com.example.justJwt.jwt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    JwtServiceImpl jwtService;



    @Autowired
    UserRepository userRepository;

  /*  @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/



    @GetMapping("/user/authenticate") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
    public ResponseEntity<Object> authTest(HttpServletRequest request) {

        String token = request.getHeader("jwt-auth-token");


        if (token != null) {
            // DB에서 해당 토큰 값으로 사용자 정보를 확인하거나 검색
            Optional<User> user = userRepository.findByAccessToken(token);

            if (user.isPresent()) {
                // 사용자 정보를 찾은 경우 - 토큰이 유효
                System.out.println("토큰이 유효합니다");
                return new ResponseEntity<>("Token Success", HttpStatus.OK);
            } else {
                // 사용자 정보를 찾지 못한 경우 - 토큰이 유효하지 않음
                System.out.println("토큰이 유효하지 않습니다");
                return new ResponseEntity<>("Token Fail", HttpStatus.UNAUTHORIZED);
            }
        } else {
            // 헤더에 토큰이 없는 경우
            System.out.println("토큰이 없습니다");
            return new ResponseEntity<>("Token Fail", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/getUser") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
    public ResponseEntity<Object> getUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("jwt-auth-token");
            Map<String, Object> tokenInfoMap = jwtService.getInfo(token);

            User user = new ObjectMapper().convertValue(tokenInfoMap.get("user"), User.class);

            return new ResponseEntity<Object>(user, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/user/signup")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {



        Optional<User> existingUser = userRepository.findById(user.getId());



        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Id already exists");
        }

        //HttpServletResponse response=null;

        //String token = jwtService.createToken(user); // 사용자 정보로 토큰 생성

        //response.setHeader("jwt-auth-token", token); // client에 token 전달
        //user.setAccessToken(token);
        userRepository.save(user);
        return new ResponseEntity<Object>("Sign-Up Success", HttpStatus.OK);
    }

    @PostMapping("/user/login") // 로그인, 토큰이 필요하지 않는 경로
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            Optional<User> DBUser = userRepository.findById(user.getId());

            User user2 = null;

            if (DBUser.isPresent()) {
                user2 = DBUser.get();

            }

            if (user2.getId().equals(user.getId()) && user2.getPassword().equals(user.getPassword())) { // 유효한 사용자일 경우
                String token = jwtService.createToken(user); // 사용자 정보로 토큰 생성

                user2.setAccessToken(token);
                userRepository.save(user2);

                return ResponseEntity.ok().header("jwt-auth-token", token).body("Set Token");
            } else {
                return ResponseEntity.badRequest().body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }



    @GetMapping("/user/id/{user_id}")
    public ResponseEntity<GetUserResponseDto > getUserById(@PathVariable String user_id) throws Exception {
        Optional<User> userOptional2 = userRepository.findById(user_id);

        if (userOptional2.isPresent()) {
            User user = userOptional2.get();

            String nickname = user.getNickname();
            String userEmail2 = user.getEmail();

            if (userEmail2 != null && nickname != null) {
                String userInfo = "nickname: " + nickname + ", email: " + userEmail2 ;
                GetUserResponseDto result = GetUserResponseDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .build();
                return ResponseEntity.ok().body(result);
//                return new ResponseEntity<>(userInfo, HttpStatus.OK);
            } else {
                // 사용자 정보 중 하나라도 null이면 해당 정보가 없다고 알림
                StringBuilder message = new StringBuilder("User's");
                if (userEmail2 == null) message.append(" email");
                if (nickname == null) message.append(" nickname");
                message.append(" no information");
                throw new Exception(message.toString());
            }
        } else {
            throw new Exception("Can't find user");
        }
    }

    @PutMapping("/user/edit/id/{user_id}")
    public ResponseEntity<Object> updateUser(@PathVariable String user_id, @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(user_id);

        if (userOptional.isPresent()) {
            User user2 = userOptional.get();

            // Update user information based on UserUpdateDto
            if (user.getEmail() != null) {
                user2.setEmail(user.getEmail());
            }
            if (user.getNickname() != null) {
                user2.setNickname(user.getNickname());
            }

            // Save the updated user
            userRepository.save(user2);

            return new ResponseEntity<>("Edit Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can't find user", HttpStatus.NOT_FOUND);
        }
    }
}
