package top.zway.fic.user.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.zway.fic.base.result.R;

/**
 * @author ZZJ
 */
@Component
@Slf4j
public class GuideInitRpcServiceFallback implements GuideInitRpcService {
    @Override
    public R<String> initGuide(Long userId) {
        log.error("rpc初始化引导看板失败, userId: {}", userId);
        return R.success("初始化失败");
    }

}
