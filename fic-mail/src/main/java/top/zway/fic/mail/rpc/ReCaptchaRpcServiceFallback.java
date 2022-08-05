package top.zway.fic.mail.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 */
@Slf4j
@Component
public class ReCaptchaRpcServiceFallback implements ReCaptchaRpcService {
    @Override
    public Boolean verify(String token) {
        log.error("rpc验证验证码失败, token: {}", token);
        return false;
    }
}
