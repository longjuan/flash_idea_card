package top.zway.fic.user.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zway.fic.base.result.R;

/**
 * @author ZZJ
 */
@FeignClient(contextId = "guideInitRpcService", name = "fic-kanban", fallback = GuideInitRpcServiceFallback.class)
public interface GuideInitRpcService {
    @GetMapping("/rpc/guide/init")
    R<String> initGuide(@RequestParam("userId") Long userId);
}
