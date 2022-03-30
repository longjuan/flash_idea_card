package top.zway.fic.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import top.zway.fic.base.result.R;
import top.zway.fic.base.result.ResultCode;

/**
 * @author ZZJ
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public <T> R<T> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常，异常原因：{}", e.getMessage(), e);
        return R.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Jsr303ValidException.class)
    public <T> R<T> handleJsr303ValidException(Jsr303ValidException e) {
        log.error("jsr303校验有误：{}", e.getMessage(), e);
        return R.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public <T> R<T> handleBizException(BizException e) {
        log.error("业务失败：{}", e.getMessage(), e);
        return R.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public <T> R<T> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("超出大小：{}", e.getMessage(), e);
        return R.failed("超出上传文件允许大小");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public <T> R<T> handleException(Exception e) {
        log.error("Exception：{}", e.getMessage(), e);
        return R.result(ResultCode.SYSTEM_EXECUTION_ERROR, e.getMessage(), null, null);
    }
}