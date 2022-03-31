package top.zway.fic.kanban.rpc;

import org.springframework.stereotype.Component;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.base.result.R;

import java.util.HashMap;

/**
 * @author ZZJ
 */
@Component
public class UserRpcServiceFallback implements UserRpcService {
    @Override
    public R<HashMap<Long, UserInfoDO>> getUserInfoDoByList(Long[] userIds) {
        HashMap<Long, UserInfoDO> ret = new HashMap<>((int) (userIds.length / 0.75) + 1);
        for (Long userId : userIds) {
            ret.put(userId, new UserInfoDO(userId, String.valueOf(userId), ""));
        }
        return R.success(ret);
    }

    @Override
    public R<HashMap<String, Long>> getUserIdByUsername(String[] email) {
        return R.success(new HashMap<>(0));
    }
}
