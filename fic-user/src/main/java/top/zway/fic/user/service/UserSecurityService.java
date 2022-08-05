package top.zway.fic.user.service;

/**
 * @author ZZJ
 */
public interface UserSecurityService {
    /**
     * 注册新用户
     * @param username 用户名
     * @param password 密码
     * @return 异常信息，成功返回null
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

    /**
     * 更换邮箱（用户名）
     * @param email 新邮箱
     * @param userid 用户id
     * @return 是否成功
     */
    boolean updateEmail(String email,Long userid);

    /**
     * 获取userid
     * @param email 用户名（邮箱）
     * @return userid
     */
    Long getUserId(String email);

    /**
     * 重置密码
     * @param email 用户名（邮箱）
     * @param password 密码
     * @return 是否成功
     */
    boolean resetPassword(String email, String password);
}
