package login.oauthtest4.domain.user.controller;

import login.oauthtest4.domain.user.User;
import login.oauthtest4.domain.user.dto.UserSignUpDto;
import login.oauthtest4.domain.user.dto.UserUpdateDto;
import login.oauthtest4.domain.user.repository.UserRepository;
import login.oauthtest4.domain.user.service.UserService;
import login.oauthtest4.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/api/auth/user/login")
    public String redirectToGoogleAuthorization() {

        return "redirect:/oauth2/authorization/google";
    }


    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

   /* @GetMapping("/api/auth/user/authenticate")
    public ResponseEntity<Object> getUser(HttpServletRequest request) {


    }*/



    @GetMapping("/api/auth/user/authenticate") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
    public ResponseEntity<Object> authTest(HttpServletRequest request) {

            Optional<String> token=jwtService.extractAccessToken(request)
                    .filter(jwtService::isTokenValid);


            if(token.isPresent()){
                System.out.println("성공!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(token);
                return new ResponseEntity<Object>("토큰이 유효합니다!!!!!!!!!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<Object>("토큰이 유효하지 않습니다!!", HttpStatus.UNAUTHORIZED);
            }

    }

    @GetMapping("api/auth/user/nickname/{nickname}")
    public ResponseEntity<Object> getUserByNickname(@PathVariable String nickname) {
        Optional<User> userOptional = userRepository.findByNickname(nickname);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String userEmail = user.getEmail();
            int userAge = user.getAge();
            String userCity = user.getCity();


            if (userEmail != null && userAge != 0 && userCity != null) {
                String userInfo = "닉네임: " + nickname + ", 이메일: " + userEmail + ", 나이: " + userAge + ", 도시: " + userCity;
                return new ResponseEntity<>(userInfo, HttpStatus.OK);
            } else {
                // 사용자 정보 중 하나라도 null이면 해당 정보가 없다고 알림
                StringBuilder message = new StringBuilder("해당 사용자의");
                if (userEmail == null) message.append(" 이메일");
                if (userAge == 0) message.append(" 나이");
                if (userCity == null) message.append(" 도시");
                message.append(" 정보가 등록되어 있지 않습니다");

                return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("api/auth/user/id/{user_id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long user_id) {
        Optional<User> userOptional2 = userRepository.findById(user_id);

        if (userOptional2.isPresent()) {
            User user = userOptional2.get();

            String nickname = user.getNickname();
            String userEmail2 = user.getEmail();
            int userAge = user.getAge();
            String userCity = user.getCity();


            if (userEmail2 != null && userAge != 0 && userCity != null) {
                String userInfo = "닉네임: " + nickname + ", 이메일: " + userEmail2 + ", 나이: " + userAge + ", 도시: " + userCity;
                return new ResponseEntity<>(userInfo, HttpStatus.OK);
            } else {
                // 사용자 정보 중 하나라도 null이면 해당 정보가 없다고 알림
                StringBuilder message = new StringBuilder("해당 사용자의");
                if (userEmail2 == null) message.append(" 이메일");
                if (userAge == 0) message.append(" 나이");
                if (userCity == null) message.append(" 도시");
                message.append(" 정보가 등록되어 있지 않습니다");

                return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/api/auth/user/edit/id/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Update user information based on UserUpdateDto
            if (userUpdateDto.getEmail() != null) {
                user.setEmail(userUpdateDto.getEmail());
            }
            if (userUpdateDto.getAge() != 0) {
                user.setAge(userUpdateDto.getAge());
            }
            if (userUpdateDto.getCity() != null) {
                user.setCity(userUpdateDto.getCity());
            }

            // Save the updated user
            userRepository.save(user);

            return new ResponseEntity<>("사용자 정보가 성공적으로 수정되었습니다", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 ID의 사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        }
    }



}
