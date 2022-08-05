package top.zway.fic.kanban.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.base.result.R;

import java.util.HashMap;

/**
 * @author ZZJ
 */
@FeignClient(name = "fic-user", contextId = "userRpcService", fallback = UserRpcServiceFallback.class)
public interface UserRpcService {
    @PostMapping("/rpc/userinfo/list")
    R<HashMap<Long, UserInfoDO>> getUserInfoDoByList(@RequestParam("userIds") Long[] userIds);

    @PostMapping("/rpc/userinfo/id")
    R<HashMap<String, Long>> getUserIdByUsername(@RequestParam("email") String[] email);
}
