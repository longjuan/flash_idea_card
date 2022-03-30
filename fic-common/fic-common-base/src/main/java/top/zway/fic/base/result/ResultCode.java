package top.zway.fic.base.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A 授权问题
 * B 业务问题
 * E 异常问题
 * @author ZZJ
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {
    SUCCESS("000", "成功"),
    USERNAME_OR_PASSWORD_ERROR("A101", "用户名或密码错误"),
    USER_NOT_EXIST("A102", "用户不存在"),
    CLIENT_AUTHENTICATION_FAILED("A201", "用户状态异常"),
    TOKEN_INVALID_OR_EXPIRED("A401", "token非法或失效"),
    ACCESS_UNAUTHORIZED("A403", "资源未授权"),
    SYSTEM_EXECUTION_ERROR("E000", "系统执行出错"),
    BUSINESS_FAILED("B000", "业务失败"),
    FLOW_LIMITING("B001", "系统限流"),
    DEGRADATION("B002", "系统功能降级"),
    ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }

}