package top.zway.fic.auth.service.impl;

import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.zway.fic.auth.service.ReCaptchaVerificationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZJ
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReCaptchaVerificationServiceImpl implements ReCaptchaVerificationService {
    @Value("${reCaptcha.client.secret}")
    private String reCaptchaClientSecret;

    private final ObjectMapper objectMapper;

    public static final String RECAPTCHA_VERIFY_URL = "https://recaptcha.net/recaptcha/api/siteverify";

    private static final String VERIFY_JSON_KEY = "success";

    @Override
    public boolean verify(String captcha) {
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("secret", reCaptchaClientSecret);
        paramMap.put("response", captcha);
        String result = HttpUtil.post(RECAPTCHA_VERIFY_URL, paramMap);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(result);
        } catch (IOException e) {
            log.error("解析json失败,json：{}", result);
            return false;
        }
        if (jsonNode == null || jsonNode.get(VERIFY_JSON_KEY) == null) {
            log.error("解析json失败,json：{}", result);
            return false;
        }
        return jsonNode.get(VERIFY_JSON_KEY).asBoolean();
    }
}
