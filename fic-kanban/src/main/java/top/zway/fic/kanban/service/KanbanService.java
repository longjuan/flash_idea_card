package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.ao.KanbanAO;
import top.zway.fic.base.entity.vo.KanbanContentVO;
import top.zway.fic.base.entity.vo.KanbanHomeVO;

import java.util.List;

/**
 * @author ZZJ
 */
public interface KanbanService {
    /**
     * 新增
     * @param kanbanAo 看板
     * @return 是否成功
     */
    boolean insertKanban(KanbanAO kanbanAo);

    /**
     * 删除
     * @param kanbanId 看板
     * @param userId 用户id
     * @return 是否成功
     */
    boolean deleteKanban(Long kanbanId, Long userId);

    /**
     * 修改
     * @param kanbanAo 看板
     * @return 是否成功
     */
    boolean updateKanban(KanbanAO kanbanAo);

    /**
     * 获取一个人的所有看板
     * @param userId 一个人
     * @return 所有看板
     */
    List<KanbanHomeVO> getMyKanbans(Long userId);

    /**
     * 获取看板内容
     * @param userId 一个人
     * @param kanbanId 看板
     * @return 看板
     */
    KanbanContentVO getKanbanContent(Long userId, Long kanbanId);

}
