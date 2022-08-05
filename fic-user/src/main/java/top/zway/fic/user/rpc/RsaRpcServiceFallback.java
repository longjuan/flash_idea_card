package top.zway.fic.user.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 */
@Component
@Slf4j
public class RsaRpcServiceFallback implements RsaRpcService {
    @Override
    public String decrypt(String uuid, String content, boolean needDelete) {
        return null;
    }
}
