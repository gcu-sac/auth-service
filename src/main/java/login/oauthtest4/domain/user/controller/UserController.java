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
    private final UserUpdateDto userUpdateDto;


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

    @GetMapping("api/auth/user/{nickname}")
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

    //@PostMapping("/api/auth/user")
    @PutMapping("/api/auth/user/{nickname}")
    public ResponseEntity<Object> updateUserDetails(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.updateUserDetails(userId, userUpdateDto);
            return new ResponseEntity<>("User details updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user details", HttpStatus.BAD_REQUEST);
        }
    }


}
