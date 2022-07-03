package top.zway.fic.user.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 */
@Slf4j
@Component
public class MailRpcServiceFallback implements MailRpcService {
    @Override
    public Boolean verifyVerificationCode(String email, String code) {
        log.error("rpc验证邮箱验证码fallback, email: {}, code: {}", email, code);
        return null;
    }
}
