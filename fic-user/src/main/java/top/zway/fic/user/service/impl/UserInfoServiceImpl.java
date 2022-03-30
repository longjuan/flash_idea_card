package top.zway.fic.user.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.user.dao.UserInfoDao;
import top.zway.fic.user.service.UserInfoService;
import top.zway.fic.user.util.OssUtil;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoDao userInfoDao;
    private final OssUtil ossUtil;
    @Value("${oss.cdn}")
    private String cdnUrl;

    @Override
    public UserInfoDO getUserInfoDo(Long userid) {
        return userInfoDao.getUserInfoDo(userid);
    }

    @Override
    public boolean updateNickname(String nickname, Long userid) {
        return userInfoDao.updateNickname(nickname, userid) > 0;
    }

    @Override
    public boolean replaceAvatar(MultipartFile avatar, Long userid) {
        // 上传oss
        String filename = ossUtil.uploadFile(avatar);
        if (StrUtil.isEmpty(filename)){
            return false;
        }
        // 保存
        int num = userInfoDao.updateAvatar(cdnUrl + filename, userid);
        return num > 0;
    }
}
