package top.zway.fic.mail.service;

/**
 * @author ZZJ
 */
public interface VerificationCodeService {
    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @return 验证码
     */
    boolean sendVerificationCode(String email);

    /**
     * 校验验证码
     *
     * @param email 邮箱
     * @param code 验证码
     * @return 是否校验成功
     */
    boolean verifyCode(String email, String code);
}
