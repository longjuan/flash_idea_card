package top.zway.fic.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.UserInfoDO;

/**
 * @author ZZJ
 */
@Mapper
public interface UserInfoDao {
    /**
     * 插入
     * @param userInfoDo 用户信息
     * @return 数量
     */
    int insertUserInfo(@Param("userInfoDo") UserInfoDO userInfoDo);

    /**
     * 根据用户id获取用户信息
     * @param userid 用户id
     * @return 用户信息
     */
    UserInfoDO getUserInfoDo(@Param("userid")Long userid);

    /**
     * 更新昵称
     * @param nickname 昵称
     * @param userid 用户id
     * @return 数量
     */
    int updateNickname(@Param("nickname")String nickname, @Param("userid")Long userid);

    /**
     * 头像地址
     * @param avatar 头像地址
     * @param userid 用户id
     * @return 数量
     */
    int updateAvatar(@Param("avatar")String avatar, @Param("userid")Long userid);
}
