package top.zway.fic.auth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.auth.service.AsymmetricEncryptionService;
import top.zway.fic.auth.service.ReCaptchaVerificationService;
import top.zway.fic.base.constant.AuthConstant;
import top.zway.fic.base.result.R;

import java.security.Principal;
import java.util.Map;

/**
 * 为了能够捕获在认证过程中出现的所有异常，这里通过复写security中的token入口
 * @author ZZJ
 */
@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final TokenEndpoint tokenEndpoint;
    private final ReCaptchaVerificationService reCaptchaVerificationService;
    private final AsymmetricEncryptionService asymmetricEncryptionService;

    @PostMapping("/oauth/token")
    public R postAccessToken(
            Principal principal,
            @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        if (AuthConstant.GRANT_TYPE_PASSWORD.equals(parameters.get(AuthConstant.GRANT_TYPE))) {
            String captchaCode = parameters.get(AuthConstant.CAPTCHA_CODE_KEY);
            if (captchaCode == null) {
                return R.failed("请先验证验证码");
            }
            if (!reCaptchaVerificationService.verify(captchaCode)) {
                return R.failed("验证失败");
            }
            String rsaUuid = parameters.get(AuthConstant.RSA_UUID_KEY);
            String password = parameters.get(AuthConstant.PASSWORD_KEY);
            if (rsaUuid == null || password == null) {
                return R.failed("密码不能为空");
            }
            String decryptPassword = asymmetricEncryptionService.decrypt(rsaUuid, password, true);
            if (decryptPassword == null) {
                return R.failed("密码解析错误");
            }
            parameters.put(AuthConstant.PASSWORD_KEY, decryptPassword);
        }
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return R.success(accessToken);
    }
}