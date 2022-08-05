package top.zway.fic.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.zway.fic.base.constant.PojoValidConstants;
import top.zway.fic.base.constant.RegexConstant;
import top.zway.fic.base.entity.dto.RegisterUserDTO;
import top.zway.fic.base.result.R;
import top.zway.fic.user.rpc.MailRpcService;
import top.zway.fic.user.rpc.ReCaptchaRpcService;
import top.zway.fic.user.rpc.RsaRpcService;
import top.zway.fic.user.service.UserSecurityService;
import top.zway.fic.web.exception.Jsr303Checker;
import top.zway.fic.web.holder.LoginUserHolder;

import javax.validation.Valid;

/**
 * @author ZZJ
 */
@RestController
@RequestMapping("/user")
@Api("用户账户密码api")
@RequiredArgsConstructor
public class UserSecurityController {
    private final UserSecurityService userSecurityService;
    private final LoginUserHolder loginUserHolder;
    private final RsaRpcService rsaRpcService;
    private final MailRpcService mailRpcService;

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    public R registerNewUser(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        Boolean verify = mailRpcService.verifyVerificationCode(registerUserDTO.getUsername(), registerUserDTO.getVerifyCode());
        if (verify == null || !verify) {
            return R.failed("邮件验证码错误或已过期");
        }
        String decrypt = rsaRpcService.decrypt(registerUserDTO.getRsaUuid(), registerUserDTO.getPassword(), true);
        if (decrypt == null) {
            return R.failed("解密失败");
        }
        boolean success = userSecurityService.registerNewUser(registerUserDTO.getUsername(), decrypt);
        return R.judge(success,"邮箱已存在");
    }

    @PutMapping("/password")
    @ApiOperation("修改密码")
    public R updatePassword(@RequestParam("oldpw") String oldpw, @RequestParam("newpw") String newpw, @RequestParam("rsaUuid") String rsaUuid) {
        if (oldpw == null || newpw == null || rsaUuid == null) {
            return R.failed("参数不能为空");
        }
        newpw = rsaRpcService.decrypt(rsaUuid, newpw, false);
        oldpw = rsaRpcService.decrypt(rsaUuid, oldpw, true);
        if (newpw == null || oldpw == null) {
            return R.failed("解密失败");
        }
        if (newpw.length() < PojoValidConstants.PASSWORD_MIN_LEN || newpw.length() > PojoValidConstants.PASSWORD_MAX_LEN){
            return R.failed("密码长度应在6-50之间");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = userSecurityService.updatePassword(oldpw, newpw, id);
        return R.judge(success,"旧密码不正确");
    }

    @PutMapping("/password/reset")
    @ApiOperation("重置密码")
    public R resetPassword(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        Boolean verify = mailRpcService.verifyVerificationCode(registerUserDTO.getUsername(), registerUserDTO.getVerifyCode());
        if (verify == null || !verify) {
            return R.failed("邮件验证码错误或已过期");
        }
        String decrypt = rsaRpcService.decrypt(registerUserDTO.getRsaUuid(), registerUserDTO.getPassword(), true);
        if (decrypt == null) {
            return R.failed("解密失败");
        }
        boolean success = userSecurityService.resetPassword(registerUserDTO.getUsername(), decrypt);
        return R.judge(success,"邮箱未注册");
    }


    @PutMapping("/email")
    @ApiOperation("修改邮箱(用户名)")
    public R updateEmail(String email){
        // XXX 更换邮箱验证码 这里暂不实现
        boolean match = RegexConstant.EMAIL_REGEX.asPredicate().test(email);
        if (!match){
            return R.failed("邮箱格式不正确");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = userSecurityService.updateEmail(email, id);
        return R.judge(success,"修改失败");
    }

}
