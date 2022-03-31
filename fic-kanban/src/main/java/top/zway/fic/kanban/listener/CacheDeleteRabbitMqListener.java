package top.zway.fic.kanban.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.zway.fic.kanban.config.CacheDeleteTTLRabbitMqConfiguration;
import top.zway.fic.kanban.service.CacheService;

/**
 * @author ZZJ
 */
@Component
@RabbitListener(queues = CacheDeleteTTLRabbitMqConfiguration.DEAD_QUEUE_NAME)
@RequiredArgsConstructor
public class CacheDeleteRabbitMqListener {
    private final CacheService cacheService;

    @RabbitHandler
    public void process(Long kanbanId) {
        cacheService.deleteKanbanCache(kanbanId);
    }
}
