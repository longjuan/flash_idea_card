package top.zway.fic.user.service;

import org.springframework.web.multipart.MultipartFile;
import top.zway.fic.base.entity.doo.UserInfoDO;

/**
 * @author ZZJ
 */
public interface UserInfoService {
    /**
     * 通过用户id查找用户信息
     * @param userid 用户id
     * @return 用户信息
     */
    UserInfoDO getUserInfoDo(Long userid);

    /**
     * 更新昵称
     * @param nickname 昵称
     * @param userid 用户id
     * @return 是否成功
     */
    boolean updateNickname(String nickname,Long userid);

    /**
     * 替换头像
     * @param avatar 头像文件上下文
     * @param userid 用户id
     * @return 是否成功
     */
    boolean replaceAvatar(MultipartFile avatar,Long userid);
}
