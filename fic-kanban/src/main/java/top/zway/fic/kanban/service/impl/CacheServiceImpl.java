package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.vo.ColumnVO;
import top.zway.fic.kanban.config.CacheDeleteTTLRabbitMqConfiguration;
import top.zway.fic.kanban.service.CacheService;
import top.zway.fic.redis.util.RedisUtils;

import java.util.List;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final RedisUtils redisUtils;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Async
    public void setKanbanCache(Long kanbanId, List<ColumnVO> columns) {
        redisUtils.set(RedisConstant.KANBAN_CACHE + kanbanId, columns, RedisConstant.KANBAN_CACHE_EXPIRE_SECOND);
    }

    @Override
    public void deleteKanbanCache(Long kanbanId) {
        redisUtils.del(RedisConstant.KANBAN_CACHE + kanbanId);
    }

    @Override
    public void doubleDelayedDeleteKanbanCache(Long kanbanId) {
        // 先删
        deleteKanbanCache(kanbanId);
        // 放进mq
        rabbitTemplate.convertAndSend(CacheDeleteTTLRabbitMqConfiguration.TTL_EXCHANGE_NAME, "", kanbanId);
    }

    @Override
    public List<ColumnVO> getKanbanCache(Long kanbanId) {
        Object obj = redisUtils.get(RedisConstant.KANBAN_CACHE + kanbanId);
        if (obj instanceof List<?>) {
            return (List<ColumnVO>) obj;
        }
        return null;
    }

    @Override
    public boolean isCooperating(Long kanbanId, Long userId) {
        // 加入协作
        redisUtils.zAdd(RedisConstant.COOPERATING_KANBAN_STATISTIC + kanbanId, userId, System.currentTimeMillis());
        // 设置过期时间
        redisUtils.expire(RedisConstant.COOPERATING_KANBAN_STATISTIC + kanbanId,
                RedisConstant.COOPERATING_KANBAN_STATISTIC_EXPIRE_SECOND);
        // 移除之前的协作信息
        redisUtils.zRemoveRangeByScore(RedisConstant.COOPERATING_KANBAN_STATISTIC + kanbanId, 0,
                System.currentTimeMillis() - RedisConstant.COOPERATING_KANBAN_STATISTIC_EXPIRE_SECOND * 1000);
        // 获取协作人数
        return redisUtils.zRank(RedisConstant.COOPERATING_KANBAN_STATISTIC + kanbanId, userId) > 1;
    }


}
