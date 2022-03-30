package top.zway.fic.auth.service;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.zway.fic.auth.dao.UserAuthDao;
import top.zway.fic.auth.entity.SecurityUser;
import top.zway.fic.base.constant.UserLoginStateConstants;
import top.zway.fic.base.entity.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserAuthDao userAuthDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> userList = userAuthDao.listUserDtoByUsername(username);
        List<UserDTO> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(UserLoginStateConstants.USERNAME_PASSWORD_ERROR);
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(UserLoginStateConstants.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(UserLoginStateConstants.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(UserLoginStateConstants.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(UserLoginStateConstants.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
