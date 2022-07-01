package top.zway.fic.mail.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

/**
 * @author ZZJ
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderUtil {
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.nickname}")
    private String nickname;
    @Value("${spring.mail.default-encoding}")
    private String encoding;

    private String mailFrom;

    private final JavaMailSender javaMailSender;

    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        mailFrom = MimeUtility.encodeText(nickname, encoding, null) + "<" + mailUsername + ">";
    }

    public void sendSimpleMail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("发送邮件失败, recipient: {}, subject: {}, content: {}", recipient, subject, content, e);
        }
    }

}
