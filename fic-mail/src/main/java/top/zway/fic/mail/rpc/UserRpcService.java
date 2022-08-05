package top.zway.fic.mail.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zway.fic.base.result.R;

import java.util.HashMap;

/**
 * @author ZZJ
 */
@FeignClient(contextId = "userRpcService", name = "fic-user", fallback = ReCaptchaRpcServiceFallback.class)
public interface UserRpcService {
    @PostMapping("/rpc/userinfo/id")
    R<HashMap<String, Long>> getUserIdByUsername(@RequestParam("email") String[] email);
}
