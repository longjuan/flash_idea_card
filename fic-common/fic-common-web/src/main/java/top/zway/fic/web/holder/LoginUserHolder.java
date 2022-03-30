package top.zway.fic.web.holder;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.zway.fic.base.entity.dto.UserDTO;
import top.zway.fic.web.exception.BizException;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息
 * @author ZZJ
 */
@Component
public class LoginUserHolder {

    public UserDTO getCurrentUser(){
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject userJsonObject = new JSONObject(userStr);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userJsonObject.getStr("user_name"));
        userDTO.setId(Convert.toLong(userJsonObject.get("id")));
        userDTO.setRoles(Convert.toList(String.class,userJsonObject.get("authorities")));
        if (userDTO.getId() == null){
            throw new BizException("未能获取当前用户");
        }
        return userDTO;
    }
}
