package top.zway.fic.auth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.auth.service.ReCaptchaVerificationService;

/**
 * @author ZZJ
 */
@RestController
@AllArgsConstructor
@Slf4j
public class ReCaptchaRpcController {
    private final ReCaptchaVerificationService reCaptchaVerificationService;

    @PostMapping("/rpc/recaptcha/verify")
    public Boolean verify(@RequestParam("token") String token) {
        return reCaptchaVerificationService.verify(token);
    }
}
