package top.zway.fic.mail.service.impl;

import cn.hutool.core.io.IoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import top.zway.fic.base.constant.RabbitMqConstants;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.bo.MailMessageBO;
import top.zway.fic.mail.rpc.ReCaptchaRpcService;
import top.zway.fic.mail.service.VerificationCodeService;
import top.zway.fic.redis.util.RedisUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ZZJ
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Value("${spring.mail.nickname}")
    private String nickname;

    private final RedisUtils redisUtils;
    private final RabbitTemplate rabbitTemplate;
    private final ReCaptchaRpcService reCaptchaRpcService;

    private static String VERIFICATION_CODE_MAIL_TEMPLATE;

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000000");

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource resource = new ClassPathResource("email_template/verification_code.html");
        VERIFICATION_CODE_MAIL_TEMPLATE = IoUtil.read(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @Override
    public String sendVerificationCode(String email, String captcha) {
        // 检查发送间隔
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisUtils.zReverseRangeByScoreWithScores(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + email,
                System.currentTimeMillis() - RedisConstant.EMAIL_VERIFICATION_CODE_SEND_INTERVAL_MILLISECOND,
                System.currentTimeMillis());
        if (typedTuples == null || typedTuples.size() > 0) {
            return "发送间隔太短，请稍后再试";
        }
        // 检查captcha
        Boolean verify = reCaptchaRpcService.verify(captcha);
        if (verify == null || !verify) {
            return "人机身份验证失败，请重新完成验证";
        }
        // 生成验证码
        int code = ThreadLocalRandom.current().nextInt(999999);
        String codeFormat = DECIMAL_FORMAT.format(code);
        // 生成邮件内容
        String contentFormat = MessageFormat.format(VERIFICATION_CODE_MAIL_TEMPLATE, codeFormat);
        MailMessageBO mailMessage = new MailMessageBO(email, nickname + "-邮箱验证码", contentFormat);
        // redis写入验证码
        redisUtils.zAdd(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + email, codeFormat, System.currentTimeMillis());
        redisUtils.expire(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + email, RedisConstant.EMAIL_VERIFICATION_CODE_EXP_TIME);
        // rabbitmq写入
        rabbitTemplate.convertAndSend(RabbitMqConstants.MAIL_SEND_EXCHANGE_NAME, "", mailMessage);
        log.info("邮箱验证码：code: {}, email: {}", codeFormat, email);
        return null;
    }

    @Override
    public boolean verifyCode(String email, String code) {
        // 删除过期的验证码
        redisUtils.zRemoveRangeByScore(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + email,
                0, System.currentTimeMillis() - (RedisConstant.EMAIL_VERIFICATION_CODE_EXP_TIME * 1000));
        // 取出验证码
        Set<ZSetOperations.TypedTuple<Object>> result = redisUtils.zReverseRangeWithScores(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + email,
                0, System.currentTimeMillis());
        for (ZSetOperations.TypedTuple<Object> tuple : result) {
            if (Objects.equals(tuple.getValue(), code)) {
                // 成功就删除该用户验证码
                redisUtils.del(RedisConstant.EMAIL_VERIFICATION_CODE_PREFIX + email);
                return true;
            }
        }
        return false;
    }
}
