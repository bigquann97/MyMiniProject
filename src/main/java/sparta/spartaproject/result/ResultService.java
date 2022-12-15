package sparta.spartaproject.result;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.DataResult;
import sparta.spartaproject.result.Status;

@Service
@Transactional(readOnly = true)
public class ResultService {

    public <T> Result getSuccessDataResult(Status status, T data) {
        return DataResult.builder()
                .success(true)
                .message(status.getMsg())
                .statusCode(status.getCode())
                .data(data)
                .build();
    }

    public Result getFailureResult(Status status) {
        return Result.baseBuilder()
                .success(false)
                .message(status.getMsg())
                .statusCode(status.getCode())
                .build();
    }

    public Result getSuccessResult(Status status) {
        return Result.baseBuilder()
                .success(true)
                .message(status.getMsg())
                .statusCode(status.getCode())
                .build();
    }

}