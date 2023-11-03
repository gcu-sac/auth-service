package login.oauthtest4.domain.user.controller;

import login.oauthtest4.domain.user.dto.UserSignUpDto;
import login.oauthtest4.domain.user.service.UserService;
import login.oauthtest4.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
    private final JwtService jwtService;


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
}
