package top.zway.fic.search.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.redis.util.RedisUtils;
import top.zway.fic.web.exception.BizException;

import javax.annotation.PostConstruct;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class PartialUpdateStrategyContext {
    private Map<SearchUpdateBO.UpdateTypeEnum, IPartialUpdateStrategy> serviceMap;


    private final List<IPartialUpdateStrategy> iPartialUpdateStrategyList;
    private final RedisUtils redisUtils;

    @PostConstruct
    private void init() {
        serviceMap = new IdentityHashMap<>((int) (iPartialUpdateStrategyList.size() / 0.75) + 1);
        for (IPartialUpdateStrategy iPartialUpdateStrategy : iPartialUpdateStrategyList) {
            serviceMap.put(iPartialUpdateStrategy.getType(), iPartialUpdateStrategy);
        }
    }

    public void invoke(SearchUpdateBO searchUpdateBO) {
        IPartialUpdateStrategy iPartialUpdateStrategy = serviceMap.get(searchUpdateBO.getUpdateType());
        if (iPartialUpdateStrategy == null) {
            throw new BizException("未找到对应处理类" + searchUpdateBO.getUpdateType());
        }
        iPartialUpdateStrategy.handlePartialUpdate(searchUpdateBO);
        redisUtils.set(RedisConstant.KANBAN_FULL_UPDATE_TIMER_PREFIX + searchUpdateBO.getKanbanId(), null,
                RedisConstant.KANBAN_FULL_UPDATE_TIMER_EXP_TIME);
    }

    public interface IPartialUpdateStrategy {
        /**
         * 处理部分更新
         *
         * @param searchUpdateBO 更新
         */
        void handlePartialUpdate(SearchUpdateBO searchUpdateBO);

        /**
         * 返回负责的type
         *
         * @return 负责的type
         */
        SearchUpdateBO.UpdateTypeEnum getType();
    }
}
