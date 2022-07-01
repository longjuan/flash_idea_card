package top.zway.fic.mail.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.base.constant.RegexConstant;
import top.zway.fic.base.result.R;
import top.zway.fic.mail.service.VerificationCodeService;
import top.zway.fic.mail.utils.MailSenderUtil;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "邮件验证码")
@Slf4j
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    @ApiOperation("获取验证码")
    @GetMapping("/mail/verification-code/{email}")
    public R getVerificationCode(@PathVariable("email") String email) {
        boolean matches = RegexConstant.EMAIL_REGEX.matcher(email).matches();
        if (!matches) {
            return R.failed("邮箱格式不正确");
        }
        boolean success = verificationCodeService.sendVerificationCode(email);
        return R.judge(success, "验证码发送间隔过短");
    }


    @GetMapping("/rpc/mail/verification-code/verify")
    public Boolean verifyVerificationCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        return verificationCodeService.verifyCode(email, code);
    }

}