package top.zway.fic.mail.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZZJ
 */
@FeignClient(contextId = "reCaptchaRpcService", name = "fic-auth", fallback = ReCaptchaRpcServiceFallback.class)
public interface ReCaptchaRpcService {
    @PostMapping("/rpc/recaptcha/verify")
    Boolean verify(@RequestParam("token") String token);
}
