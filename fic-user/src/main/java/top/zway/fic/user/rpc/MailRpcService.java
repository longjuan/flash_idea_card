package top.zway.fic.user.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZZJ
 */
@FeignClient(contextId = "mailRpcService", name = "fic-mail", fallback = MailRpcServiceFallback.class)
public interface MailRpcService {
    @GetMapping("/rpc/mail/verification-code/verify")
    Boolean verifyVerificationCode(@RequestParam("email") String email, @RequestParam("code") String code);
}
