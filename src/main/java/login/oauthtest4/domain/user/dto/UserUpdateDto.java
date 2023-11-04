package login.oauthtest4.domain.user.dto;

// UserUpdateDto.java

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private String email;
    private Integer age;
    private String city;
}
