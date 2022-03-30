package top.zway.fic.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zway.fic.base.result.R;
import top.zway.fic.base.result.ResultCode;

/**
 * @author ZZJ
 */
@Slf4j
@RestControllerAdvice
public class GatewayExceptionRestControllerAdviceHandler {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public <T> R<T> handleException(Exception e) {
        log.error("Exception：{}", e.getMessage(), e);
        return R.result(ResultCode.SYSTEM_EXECUTION_ERROR, e.getMessage(), null, null);
    }
}
