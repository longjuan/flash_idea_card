package top.zway.fic.search.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.base.entity.doo.CardDO;
import top.zway.fic.base.entity.doo.KanbanColumnDO;
import top.zway.fic.base.entity.doo.TagDO;
import top.zway.fic.redis.util.RedisUtils;
import top.zway.fic.search.config.SearchUpdateRabbitMqConfig;
import top.zway.fic.search.dao.CardDao;
import top.zway.fic.search.dao.ColumnDao;
import top.zway.fic.search.dao.TagDao;
import top.zway.fic.search.handler.CardPartialUpdateHandler;
import top.zway.fic.search.handler.ColumnPartialUpdateHandler;
import top.zway.fic.search.handler.KanbanPartialUpdateHandler;
import top.zway.fic.search.handler.TagPartialUpdateHandler;
import top.zway.fic.search.service.SearchService;

import java.util.List;

/**
 * @author ZZJ
 */
@Component
@RabbitListener(queues = SearchUpdateRabbitMqConfig.FULL_UPDATE_DEAD_QUEUE_NAME)
@RequiredArgsConstructor
public class FullUpdateListener {
    private final RedisUtils redisUtils;
    private final RabbitTemplate rabbitTemplate;
    private final SearchService searchService;

    @RabbitHandler
    public void process(SearchUpdateBO searchUpdateBO) {
        Long kanbanId = searchUpdateBO.getKanbanId();
        Object o = redisUtils.get(RedisConstant.KANBAN_FULL_UPDATE_TIMER_PREFIX + kanbanId);
        if (o == null) {
            // 全量更新
            searchService.fullUpdate(searchUpdateBO.getKanbanId());
        } else {
            // 继续等待
            rabbitTemplate.convertAndSend(SearchUpdateRabbitMqConfig.FULL_UPDATE_TTL_EXCHANGE_NAME, "", searchUpdateBO);
        }
    }
}
