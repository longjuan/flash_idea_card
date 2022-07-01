package top.zway.fic.mail.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.zway.fic.base.constant.RabbitMqConstants;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.bo.MailMessageBO;
import top.zway.fic.mail.utils.MailSenderUtil;
import top.zway.fic.redis.util.RedisUtils;

/**
 * @author ZZJ
 */
@Component
@RabbitListener(queues = RabbitMqConstants.MAIL_SEND_QUEUE_NAME)
@RequiredArgsConstructor
public class MailSendListener {
    private final RedisUtils redisUtils;
    private final MailSenderUtil mailSenderUtil;

    @RabbitHandler
    public void process(MailMessageBO mailMessageBO) {
        redisUtils.expire(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + mailMessageBO.getTo(), RedisConstant.EMAIL_VERIFICATION_CODE_EXP_TIME);
        mailSenderUtil.sendSimpleMail(mailMessageBO.getTo(), mailMessageBO.getSubject(), mailMessageBO.getContent());
    }
}
