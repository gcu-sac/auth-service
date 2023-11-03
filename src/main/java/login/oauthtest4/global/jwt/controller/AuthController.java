package login.oauthtest4.global.jwt.controller;

import javax.servlet.http.HttpServletRequest;

import login.oauthtest4.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {


   /* JwtService jwtService;

    @GetMapping("/api/auth/user/authenticate") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
    public ResponseEntity<Object> getUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("jwt-auth-token");

            if(jwtService.isTokenValid(token)){
                System.out.println("성공");
;                return new ResponseEntity<Object>("토큰이 유효합니다", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<Object>("토큰이 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
            }

        } catch(Exception e) {
            return new ResponseEntity<Object>("토큰이 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
        }
    }*/


}