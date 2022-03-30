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
}
