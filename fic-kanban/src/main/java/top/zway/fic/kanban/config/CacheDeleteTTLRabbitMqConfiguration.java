package top.zway.fic.kanban.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZJ
 */
@Configuration
public class CacheDeleteTTLRabbitMqConfiguration {
    public static final String TTL_EXCHANGE_NAME = "cache_delete_ttl_fanout_exchange";
    public static final String TTL_QUEUE_NAME = "cache.delete.ttl.queue";
    public static final String DEAD_EXCHANGE_NAME = "cache_delete_dead_fanout_exchange";
    public static final String DEAD_QUEUE_NAME = "cache.delete.dead.queue";
    public static final int TTL_MILLISECOND = 500;

    // 声明交换机

    @Bean
    public FanoutExchange cacheDeleteTTLExchange() {
        return new FanoutExchange(TTL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange cacheDeleteDeadExchange() {
        return new FanoutExchange(DEAD_EXCHANGE_NAME, true, false);
    }

    // 声明队列

    @Bean
    public Queue cacheDeleteTTLQueue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", TTL_MILLISECOND);
        map.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        return new Queue(TTL_QUEUE_NAME, true, false, false, map);
    }

    @Bean
    public Queue cacheDeleteDeadQueue() {
        return new Queue(DEAD_QUEUE_NAME, true, false, false);
    }

    // 绑定

    @Bean
    public Binding smsTTLDirectBinding(@Qualifier("cacheDeleteTTLExchange") FanoutExchange cacheDeleteTTLExchange,
                                       @Qualifier("cacheDeleteTTLQueue") Queue cacheDeleteTTLQueue) {
        return BindingBuilder.bind(cacheDeleteTTLQueue).to(cacheDeleteTTLExchange);
    }

    @Bean
    public Binding smsTTLMessageDirectBinding(@Qualifier("cacheDeleteDeadExchange") FanoutExchange cacheDeleteDeadExchange,
                                              @Qualifier("cacheDeleteDeadQueue") Queue cacheDeleteDeadQueue) {
        return BindingBuilder.bind(cacheDeleteDeadQueue).to(cacheDeleteDeadExchange);
    }
}
