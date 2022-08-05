package top.zway.fic.user.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 */
@Component
@Slf4j
public class ReCaptchaRpcServiceFallback implements ReCaptchaRpcService {
    @Override
    public Boolean verify(String token) {
        log.error("rpc验证验证码失败, token: {}", token);
        return false;
    }
}
