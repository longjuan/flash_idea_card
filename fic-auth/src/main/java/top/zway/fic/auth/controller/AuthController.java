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

    @PostMapping("/oauth/token")
    public Object postAccessToken(
            Principal principal,
            @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        // XXX 登录验证码 暂不实现
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return R.success(accessToken);
    }
}