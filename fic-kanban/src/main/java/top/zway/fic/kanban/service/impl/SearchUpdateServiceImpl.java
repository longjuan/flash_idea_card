package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.kanban.config.SearchUpdateRabbitMqConfig;
import top.zway.fic.kanban.service.SearchUpdateService;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchUpdateServiceImpl implements SearchUpdateService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Async
    public void update(SearchUpdateBO searchUpdateBO) {
        rabbitTemplate.convertAndSend(SearchUpdateRabbitMqConfig.DATA_UPDATE_EXCHANGE_NAME, "", searchUpdateBO);
    }
}
