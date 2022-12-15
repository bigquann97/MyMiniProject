package sparta.spartaproject.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.spartaproject.dto.user.UserDto;
import sparta.spartaproject.result.ResultService;
import sparta.spartaproject.service.user.UserService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static sparta.spartaproject.dto.user.UserDto.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResultService resultService;

    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody @Valid SignUpReq signUpReq) {
        SignupRes data = userService.signup(signUpReq);
        Result result = resultService.getSuccessDataResult(Status.S_USER_CREATED, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginReq loginReq, HttpServletResponse response) {
        userService.login(loginReq, response);
        String data = response.getHeader("Authorization");
        Result result = resultService.getSuccessDataResult(Status.S_USER_LOGIN, data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
