package top.zway.fic.auth.service;

/**
 * @author ZZJ
 */
public interface ReCaptchaVerificationService {
    /**
     * 验证验证码
     * @param captcha 验证码
     * @return 验证结果
     */
    boolean verify(String captcha);
}
