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

    @Size(min = PojoValidConstants.PASSWORD_MIN_LEN, max = PojoValidConstants.PASSWORD_MAX_LEN, message = "密码长度应在6-50之间")
    @NotBlank(message = "密码不能为空")
    private String password;
}
