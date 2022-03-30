package top.zway.fic.base.result;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZZJ
 */
@Data
public class R<T> implements Serializable {
    private String code;

    private String msg;

    private Integer total;

    private T data;

    private void setResultEnum(ResultCode resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public static <T> R<T> result(ResultCode resultEnum, String message, T data, Integer total) {
        R<T> result = new R<>();
        result.setResultEnum(resultEnum);
        if (!StrUtil.isEmptyIfStr(message)) {
            result.setMsg(message);
        }
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static <T> R<T> success(T data, Integer total) {
        return result(ResultCode.SUCCESS,null,data,total);
    }

    public static <T> R<T> success(T data) {
        return success(data, null);
    }

    public static <T> R<T> success() {
        return success(null, null);
    }

    public static <T> R<T> failed(String message) {
        return result(ResultCode.BUSINESS_FAILED, message, null, null);
    }

    public static R failed(ResultCode resultCode) {
        return result(resultCode,null,null,null);
    }

    public static <T> R<T> failed() {
        return failed((String) null);
    }

    public static <T> R<T> judge(boolean success) {
        return success? success(null, null) : failed();
    }

    public static <T> R<T> judge(boolean success,String failedMessage) {
        return success? success(null, null) : failed(failedMessage);
    }
}
