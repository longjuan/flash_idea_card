package top.zway.fic.mail.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zway.fic.base.constant.RabbitMqConstants;

/**
 * @author ZZJ
 */
@Configuration
public class MailSendRabbitMqConfig {
    @Bean
    public FanoutExchange mailSendExchange() {
        return new FanoutExchange(RabbitMqConstants.MAIL_SEND_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue mailSendQueue() {
        return new Queue(RabbitMqConstants.MAIL_SEND_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding mailSendBinding(@Qualifier("mailSendExchange") FanoutExchange mailSendExchange,
                                   @Qualifier("mailSendQueue") Queue mailSendQueue) {
        return BindingBuilder.bind(mailSendQueue).to(mailSendExchange);
    }
}
