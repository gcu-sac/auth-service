package login.oauthtest4.domain.user.service;

import login.oauthtest4.domain.user.Role;
import login.oauthtest4.domain.user.User;
import login.oauthtest4.domain.user.dto.UserSignUpDto;
import login.oauthtest4.domain.user.dto.UserUpdateDto;
import login.oauthtest4.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .city(userSignUpDto.getCity())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    public void updateUserDetails(Long userId, UserUpdateDto userUpdateDto) {
        Optional<User> userOptional = userRepository.findById(userId);

        userOptional.ifPresent(user -> {
            // Update user details if the DTO has non-null values
            if (userUpdateDto.getEmail() != null) {
                user.setEmail(userUpdateDto.getEmail());
            }
            if (userUpdateDto.getAge() != null) {
                user.setAge(userUpdateDto.getAge());
            }
            if (userUpdateDto.getCity() != null) {
                user.setCity(userUpdateDto.getCity());
            }
            userRepository.save(user);
        });
    }


}


