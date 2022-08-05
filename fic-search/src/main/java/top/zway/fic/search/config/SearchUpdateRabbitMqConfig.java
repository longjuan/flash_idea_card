package top.zway.fic.search.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zway.fic.base.constant.RabbitMqConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZJ
 */
@Configuration
public class SearchUpdateRabbitMqConfig {

    // 声明交换机

    @Bean
    public FanoutExchange dataUpdateExchange() {
        return new FanoutExchange(RabbitMqConstants.DATA_UPDATE_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fullUpdateDeadExchange() {
        return new FanoutExchange(RabbitMqConstants.FULL_UPDATE_DEAD_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fullUpdateTTLExchange() {
        return new FanoutExchange(RabbitMqConstants.FULL_UPDATE_TTL_EXCHANGE_NAME, true, false);
    }

    // 声明队列

    @Bean
    public Queue dataUpdateQueue() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("x-max-length", RabbitMqConstants.DATA_UPDATE_WAITING_MAX_LENGTH);
        return new Queue(RabbitMqConstants.DATA_UPDATE_QUEUE_NAME, true, false, false, map);
    }

    @Bean
    public Queue fullUpdateTTLQueue() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("x-message-ttl", RabbitMqConstants.FULL_UPDATE_TTL_MILLISECOND);
        map.put("x-dead-letter-exchange", RabbitMqConstants.FULL_UPDATE_DEAD_EXCHANGE_NAME);
        map.put("x-max-length", RabbitMqConstants.DATA_UPDATE_WAITING_MAX_LENGTH);
        return new Queue(RabbitMqConstants.FULL_UPDATE_TTL_QUEUE_NAME, true, false, false, map);
    }

    @Bean
    public Queue fullUpdateDeadQueue() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("x-max-length", RabbitMqConstants.FULL_UPDATE_WAITING_MAX_LENGTH);
        return new Queue(RabbitMqConstants.FULL_UPDATE_DEAD_QUEUE_NAME, true, false, false, map);
    }


    // 绑定

    @Bean
    public Binding dataUpdateFanoutBinding(@Qualifier("dataUpdateExchange") FanoutExchange dataUpdateExchange,
                                           @Qualifier("dataUpdateQueue") Queue dataUpdateQueue) {
        return BindingBuilder.bind(dataUpdateQueue).to(dataUpdateExchange);
    }

    @Bean
    public Binding dataUpdateToTTLFanoutBinding(@Qualifier("dataUpdateExchange") FanoutExchange dataUpdateExchange,
                                                @Qualifier("fullUpdateTTLQueue") Queue fullUpdateTTLQueue) {
        return BindingBuilder.bind(fullUpdateTTLQueue).to(dataUpdateExchange);
    }

    @Bean
    public Binding fullUpdateDeadFanoutBinding(@Qualifier("fullUpdateDeadExchange") FanoutExchange fullUpdateDeadExchange,
                                               @Qualifier("fullUpdateDeadQueue") Queue fullUpdateDeadQueue) {
        return BindingBuilder.bind(fullUpdateDeadQueue).to(fullUpdateDeadExchange);
    }

    @Bean
    public Binding fullUpdateTTLFanoutBinding(@Qualifier("fullUpdateTTLExchange") FanoutExchange fullUpdateTTLExchange,
                                              @Qualifier("fullUpdateTTLQueue") Queue fullUpdateTTLQueue) {
        return BindingBuilder.bind(fullUpdateTTLQueue).to(fullUpdateTTLExchange);
    }

}
