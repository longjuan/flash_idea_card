package top.zway.fic.user.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZZJ
 */
@FeignClient(contextId = "rsaRpcService", name = "fic-auth", fallback = RsaRpcServiceFallback.class)
public interface RsaRpcService {
    @PostMapping("/rpc/rsa/decrypt")
    String decrypt(@RequestParam("uuid") String uuid, @RequestParam("content") String content,
                          @RequestParam("needDelete") boolean needDelete);
}
