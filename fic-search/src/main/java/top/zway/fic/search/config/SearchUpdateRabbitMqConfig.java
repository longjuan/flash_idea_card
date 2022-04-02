package top.zway.fic.search.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZJ
 */
@Configuration
public class SearchUpdateRabbitMqConfig {
    public static final String DATA_UPDATE_EXCHANGE_NAME = "data_update_fanout_exchange";
    public static final String DATA_UPDATE_QUEUE_NAME = "data.update.queue";
    public static final String FULL_UPDATE_TTL_QUEUE_NAME = "full.update.ttl.queue";
    public static final String FULL_UPDATE_DEAD_EXCHANGE_NAME = "full_update_dead_fanout_exchange";
    public static final String FULL_UPDATE_DEAD_QUEUE_NAME = "full.update.dead.queue";
    public static final int TTL_MILLISECOND = 1000 * 60 * 30;
    public static final String FULL_UPDATE_TTL_EXCHANGE_NAME = "full_update_ttl_fanout_exchange";
    public static final int WAITING_MAX_LENGTH = 10000;
    public static final int FULL_UPDATE_WAITING_MAX_LENGTH = 1000;

    // 声明交换机

    @Bean
    public FanoutExchange dataUpdateExchange() {
        return new FanoutExchange(DATA_UPDATE_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fullUpdateDeadExchange() {
        return new FanoutExchange(FULL_UPDATE_DEAD_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fullUpdateTTLExchange() {
        return new FanoutExchange(FULL_UPDATE_TTL_EXCHANGE_NAME, true, false);
    }

    // 声明队列

    @Bean
    public Queue dataUpdateQueue() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("x-max-length", WAITING_MAX_LENGTH);
        return new Queue(DATA_UPDATE_QUEUE_NAME, true, false, false, map);
    }

    @Bean
    public Queue fullUpdateTTLQueue() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("x-message-ttl", TTL_MILLISECOND);
        map.put("x-dead-letter-exchange", FULL_UPDATE_DEAD_EXCHANGE_NAME);
        map.put("x-max-length", WAITING_MAX_LENGTH);
        return new Queue(FULL_UPDATE_TTL_QUEUE_NAME, true, false, false, map);
    }

    @Bean
    public Queue fullUpdateDeadQueue() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("x-max-length", FULL_UPDATE_WAITING_MAX_LENGTH);
        return new Queue(FULL_UPDATE_DEAD_QUEUE_NAME, true, false, false, map);
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
