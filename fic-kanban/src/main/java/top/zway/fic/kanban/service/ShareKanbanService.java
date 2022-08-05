package top.zway.fic.kanban.service;

/**
 * @author ZZJ
 */
public interface ShareKanbanService {

    /**
     * 改变收藏状态
     * @param kanbanId 看板
     * @param userId 用户
     * @param collected 收藏状态
     * @return 是否成功
     */
    boolean updateCollectState(Long kanbanId, Long userId, Boolean collected);

    /**
     * 退出协作
     * @param kanbanId 看板
     * @param userId 用户
     * @param actionUserId 操作用户
     * @return 是否成功
     */
    boolean deleteShare(Long kanbanId, Long userId, Long actionUserId);
}
