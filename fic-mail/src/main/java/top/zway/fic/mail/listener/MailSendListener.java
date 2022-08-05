package top.zway.fic.mail.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.zway.fic.base.constant.RabbitMqConstants;
import top.zway.fic.base.entity.bo.MailMessageBO;
import top.zway.fic.mail.utils.MailSenderUtil;

/**
 * @author ZZJ
 */
@Component
@RabbitListener(queues = RabbitMqConstants.MAIL_SEND_QUEUE_NAME, concurrency = "5-10")
@RequiredArgsConstructor
@Slf4j
public class MailSendListener {
    @Value("${mail.sending.allow}")
    private boolean allowSending;

    private final MailSenderUtil mailSenderUtil;

    @RabbitHandler
    public void process(MailMessageBO mailMessageBO) {
        if (allowSending) {
            mailSenderUtil.sendSimpleMail(mailMessageBO.getTo(), mailMessageBO.getSubject(), mailMessageBO.getContent());
        }else {
            log.info("邮件发送被禁止,邮件信息:{}", mailMessageBO);
        }
    }
}
