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
public class InvitationReminderRabbitMqConfig {
    @Bean
    public FanoutExchange invitationReminderExchange() {
        return new FanoutExchange(RabbitMqConstants.INVITATION_REMINDER_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue invitationReminderQueue() {
        return new Queue(RabbitMqConstants.INVITATION_REMINDER_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding invitationReminderBinding(@Qualifier("invitationReminderExchange") FanoutExchange invitationReminderExchange,
                                   @Qualifier("invitationReminderQueue") Queue invitationReminderQueue) {
        return BindingBuilder.bind(invitationReminderQueue).to(invitationReminderExchange);
    }
}
