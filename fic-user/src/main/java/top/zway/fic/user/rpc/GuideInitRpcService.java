package top.zway.fic.user.rpc;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zway.fic.base.result.R;

/**
 * @author ZZJ
 */
@FeignClient("fic-kanban")
public interface GuideInitRpcService {
    @GetMapping("/rpc/guide/init")
    R<String> initGuide(@RequestParam("userId") Long userId);
}
