package top.zway.fic.mail.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.zway.fic.base.result.R;

import java.util.HashMap;

/**
 * @author ZZJ
 */
@Slf4j
@Component
public class UserRpcServiceFallback implements UserRpcService {
    @Override
    public R<HashMap<String, Long>> getUserIdByUsername(String[] email) {
        log.error("rpc获取用户信息失败, email: {}", email);
        return R.failed("获取用户id失败");
    }
}
