package top.zway.fic.user.service;

/**
 * @author ZZJ
 */
public interface UserSecurityService {
    /**
     * 注册新用户
     * @param username 用户名
     * @param password 密码
     * @return 是否成功
     */
    boolean registerNewUser(String username, String password);

    /**
     * 更改密码
     * @param oldpw 旧密码
     * @param newpd 新密码
     * @param userid 用户id
     * @return 是否成功
     */
    boolean updatePassword(String oldpw, String newpd, Long userid);
}
