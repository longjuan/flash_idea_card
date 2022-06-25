package top.zway.fic.auth.service;

import top.zway.fic.base.entity.vo.CaptchaVO;

/**
 * @author ZZJ
 */
public interface CaptchaService {
    /**
     * 获取验证码
     *
     * @return 验证码
     */
    CaptchaVO generateCaptcha();

    /**
     * 验证验证码
     *
     * @param captcha 验证码
     * @param uuid    uuid
     * @return 验证结果
     */
    boolean validateCaptcha(String captcha, String uuid);
}
