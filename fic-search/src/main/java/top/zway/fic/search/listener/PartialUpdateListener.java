package top.zway.fic.search.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.search.config.SearchUpdateRabbitMqConfig;
import top.zway.fic.search.handler.PartialUpdateStrategyContext;

/**
 * @author ZZJ
 */
@Component
@RabbitListener(queues = SearchUpdateRabbitMqConfig.DATA_UPDATE_QUEUE_NAME)
@RequiredArgsConstructor
public class PartialUpdateListener {
    private final PartialUpdateStrategyContext partialUpdateStrategyContext;

    @RabbitHandler
    public void process(SearchUpdateBO searchUpdateBO) {
        partialUpdateStrategyContext.invoke(searchUpdateBO);
    }
}
