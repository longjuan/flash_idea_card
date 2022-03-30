package top.zway.fic.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.zway.fic.base.constant.PojoValidConstants;
import top.zway.fic.base.entity.dto.RegisterUserDTO;
import top.zway.fic.base.result.R;
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

    @PostMapping("/register")
    @ApiOperation("新用户注册")
    public R registerNewUser(@Valid @RequestBody RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        boolean success = userSecurityService.registerNewUser(registerUserDTO.getUsername(), registerUserDTO.getPassword());
        if (success){
            return R.success();
        }else {
            return R.failed("用户名已存在");
        }
    }

    @PutMapping("/password")
    @ApiOperation("修改密码")
    public R updatePassword(String oldpw, String newpd){
        if (newpd.length() < PojoValidConstants.PASSWORD_MIN_LEN || newpd.length() > PojoValidConstants.PASSWORD_MAX_LEN){
            return R.failed("密码长度应在6-50之间");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = userSecurityService.updatePassword(oldpw, newpd, id);
        if (!success){
            return R.failed("旧密码不正确");
        }
        return R.success();
    }


}
