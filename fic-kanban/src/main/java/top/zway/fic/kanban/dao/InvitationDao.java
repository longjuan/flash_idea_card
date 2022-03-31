package top.zway.fic.kanban.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.InvitationDO;

import java.util.Date;
import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface InvitationDao {
    /**
     * 插入
     *
     * @param record 邀请
     * @return 数量
     */
    int insert(InvitationDO record);

    /**
     * 查日期之后的邀请
     *
     * @param invitedUser 用户
     * @param date  日期
     * @return 信息
     */
    List<InvitationDO> selectByInvitedUser(@Param("invitedUser") Long invitedUser, @Param("date") Date date);

    /**
     * 根据主键查信息
     * @param invitationId 邀请id
     * @return 信息
     */
    InvitationDO selectByInvitationId(@Param("invitationId") Long invitationId);

    /**
     * 更新状态
     * @param userId 被邀请用户
     * @param kanbanId 看板
     * @param state 状态
     * @return 数量
     */
    int updateState(@Param("userId") Long userId, @Param("kanbanId") Long kanbanId, @Param("state") Integer state);
}
