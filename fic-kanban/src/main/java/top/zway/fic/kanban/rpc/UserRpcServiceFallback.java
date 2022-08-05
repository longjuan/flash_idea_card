package top.zway.fic.kanban.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.base.result.R;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author ZZJ
 */
@Slf4j
@Component
public class UserRpcServiceFallback implements UserRpcService {
    @Override
    public R<HashMap<Long, UserInfoDO>> getUserInfoDoByList(Long[] userIds) {
        log.error("rpc获取用户信息失败, userIds: {}", Arrays.toString(userIds));
        HashMap<Long, UserInfoDO> ret = new HashMap<>((int) (userIds.length / 0.75) + 1);
        for (Long userId : userIds) {
            ret.put(userId, new UserInfoDO(userId, String.valueOf(userId), ""));
        }
        return R.success(ret);
    }

    @Override
    public R<HashMap<String, Long>> getUserIdByUsername(String[] email) {
        log.error("rpc获取用户id失败, email: {}", Arrays.toString(email));
        return R.success(new HashMap<>(0));
    }
}
