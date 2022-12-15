package sparta.spartaproject.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.user.UserDto;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.AlreadyExistUserException;
import sparta.spartaproject.exception.WrongPwException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.jwt.JwtUtil;
import sparta.spartaproject.repository.user.UserRepository;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto.SignupRes signup(UserDto.SignUpReq signUpReq) {
        validateSignupReq(signUpReq);
        changeReqPwEncoded(signUpReq);
        User user = userRepository.save(User.of(signUpReq));
        return UserDto.SignupRes.of(user);
    }

    @Transactional(readOnly = true)
    public void login(UserDto.LoginReq loginReq, HttpServletResponse response) {
        User user = userRepository.findUserByLoginId(loginReq.getLoginId()).orElseThrow(NotExistUserException::new);
        validateLogin(loginReq, user);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getLoginId(), user.getRole()));
    }

    private void validateLogin(UserDto.LoginReq loginReq, User user) {
        if(!passwordEncoder.matches(loginReq.getLoginPw(), user.getLoginPw()))
            throw new WrongPwException();
    }

    private void changeReqPwEncoded(UserDto.SignUpReq signUpReq) {
        String encodedPw = passwordEncoder.encode(signUpReq.getLoginPw());
        signUpReq.changePw(encodedPw);
    }

    private void validateSignupReq(UserDto.SignUpReq signUpReq) {
        if(! signUpReq.validatePw())
            throw new WrongPwException();
        if(userRepository.existsByLoginId(signUpReq.getLoginId()))
            throw new AlreadyExistUserException();
    }
}
