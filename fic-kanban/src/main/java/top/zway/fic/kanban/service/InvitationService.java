package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.vo.InvitationVO;

import java.util.List;

/**
 * @author ZZJ
 */
public interface InvitationService {
    /**
     * 邀请加入
     * @param invitedUser 受邀用户
     * @param kanbanId 看板
     * @param sendUser 邀请人
     * @return 是否成功
     */
    boolean invite(String invitedUser, Long kanbanId, Long sendUser);

    /**
     * 查被邀请记录 一个月内
     * @param invitedUser 被邀请用户
     * @return 记录
     */
    List<InvitationVO> getInvitation(Long invitedUser);

    /**
     * 同意邀请
     * @param invitationId 邀请id
     * @param userid 用户
     * @return 成功
     */
    boolean accept(Long invitationId, Long userid);
}
