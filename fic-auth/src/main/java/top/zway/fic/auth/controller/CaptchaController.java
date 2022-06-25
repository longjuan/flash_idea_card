package top.zway.fic.auth.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.auth.service.CaptchaService;
import top.zway.fic.base.entity.vo.CaptchaVO;
import top.zway.fic.base.result.R;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@Api("验证码")
@Slf4j
public class CaptchaController {
    private final CaptchaService captchaService;

    @GetMapping("/oauth/captcha")
    public R<CaptchaVO> captcha() {
        return R.success(captchaService.generateCaptcha());
    }

    @PostMapping("/rpc/captcha/validate")
    public boolean validate(@RequestParam("captcha") String captcha, @RequestParam("uuid") String uuid) {
        return captchaService.validateCaptcha(captcha, uuid);
    }

}
