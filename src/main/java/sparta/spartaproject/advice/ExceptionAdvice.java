package sparta.spartaproject.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.spartaproject.exception.*;
import sparta.spartaproject.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResultService resultService;

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result alreadyExistUserException() {
        return resultService.getFailureResult(Status.F_USER_ALREADY_EXIST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidTokenException() {
        return resultService.getFailureResult(Status.F_INVALID_TOKEN);
    }

    @ExceptionHandler(NotExistPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistPostException() {
        return resultService.getFailureResult(Status.F_POST_NOT_EXIST);
    }

    @ExceptionHandler(NotExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistUserException() {
        return resultService.getFailureResult(Status.F_USER_NOT_EXIST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result UnauthorizedException() {
        return resultService.getFailureResult(Status.F_UNAUTHORIZED);
    }

    @ExceptionHandler(WrongPwException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result wrongPwException() {
        return resultService.getFailureResult(Status.F_USER_WRONG_PW);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentException() {
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result methodArgumentNotValidException() {
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result httpRequestMethodNotSupportedException() {
        return resultService.getFailureResult(Status.F_UNAUTHORIZED);
    }

}
