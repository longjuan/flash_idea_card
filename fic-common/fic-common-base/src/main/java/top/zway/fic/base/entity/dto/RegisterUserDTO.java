package top.zway.fic.base.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zway.fic.base.constant.PojoValidConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO implements Serializable {
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "请输入邮件验证码")
    private String verifyCode;

    @NotBlank(message = "请携带rsa的uuid")
    private String rsaUuid;
}
