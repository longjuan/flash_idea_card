package top.zway.fic.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zway.fic.base.constant.RoleConstant;
import top.zway.fic.base.entity.doo.RoleUserDO;
import top.zway.fic.base.entity.doo.UserDO;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.user.dao.RoleUserDao;
import top.zway.fic.user.dao.UserDao;
import top.zway.fic.user.dao.UserInfoDao;
import top.zway.fic.user.rpc.GuideInitRpcService;
import top.zway.fic.user.service.UserSecurityService;

import java.util.Collections;
import java.util.List;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserDao userDao;
    private final RoleUserDao roleUserDao;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoDao userInfoDao;

    private final GuideInitRpcService guideInitRpcService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean registerNewUser(String username, String password) {
        // 检查用户名是否存在
        int countUsername = userDao.countUsername(username);
        if (countUsername > 0) {
            return false;
        }
        UserDO userDO = new UserDO(null, username, passwordEncoder.encode(password), 1);
        // 插入用户登录信息
        int insertUser = userDao.insertUser(userDO);
        if (insertUser < 1) {
            return false;
        }
        // 插入用户权限
        List<RoleUserDO> roleUserDoS = Collections.singletonList(new RoleUserDO(RoleConstant.REGISTERED, userDO.getId()));
        roleUserDao.insertRoleUser(roleUserDoS);
        // 插入用户信息
        UserInfoDO userInfoDO = new UserInfoDO(userDO.getId(), username, "");
        userInfoDao.insertUserInfo(userInfoDO);
        // 初始化引导看板
        guideInitRpcService.initGuide(userDO.getId());
        return true;
    }

    @Override
    public boolean updatePassword(String oldpw, String newpd, Long userid) {
        // 检查旧密码输入是否正确
        String password = userDao.getUserDoById(userid).getPassword();
        boolean matches = passwordEncoder.matches(oldpw, password);
        if (!matches) {
            return false;
        }
        // 更新
        int num = userDao.updatePassword(passwordEncoder.encode(newpd), userid);
        return num > 0;
    }

    @Override
    public boolean updateEmail(String email, Long userid) {
        int num = userDao.updateUsername(email, userid);
        return num > 0;
    }

    @Override
    public Long getUserId(String email) {
        return userDao.getUserId(email);
    }

    @Override
    public boolean resetPassword(String email, String password) {
        return userDao.updatePasswordByUsername(passwordEncoder.encode(password), email) > 0;
    }
}
