package top.zway.fic.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zway.fic.auth.service.CaptchaService;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.vo.CaptchaVO;
import top.zway.fic.redis.util.RedisUtils;

/**
 * @author ZZJ
 */
@RequiredArgsConstructor
@Service
public class CaptchaServiceImpl implements CaptchaService {
    private final RedisUtils redisUtils;

    @Override
    public CaptchaVO generateCaptcha() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 40, 4, 20);
        String uuid = IdUtil.simpleUUID();
        redisUtils.set(RedisConstant.CAPTCHA_CODE + uuid, circleCaptcha.getCode(), RedisConstant.CAPTCHA_CODE_EXP_TIME);
        return new CaptchaVO(circleCaptcha.getImageBase64Data(), uuid);
    }

    @Override
    public boolean validateCaptcha(String captcha, String uuid) {
        Object obj = redisUtils.get(RedisConstant.CAPTCHA_CODE + uuid);
        redisUtils.del(RedisConstant.CAPTCHA_CODE + uuid);
        if (obj instanceof String) {
            String code = (String) obj;
            return StrUtil.equals(code, captcha);
        }
        return false;
    }
}
