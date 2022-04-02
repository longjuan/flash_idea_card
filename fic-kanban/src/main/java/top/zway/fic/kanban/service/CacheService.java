package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.vo.ColumnVO;

import java.util.List;

/**
 * @author ZZJ
 */
public interface CacheService {

    /**
     * 缓存看板内容
     * @param kanbanId 看板id
     * @param columns 看板内容VO
     */
    void setKanbanCache(Long kanbanId, List<ColumnVO> columns);

    /**
     *  删除看板缓存
     * @param kanbanId 看板
     */
    void deleteKanbanCache(Long kanbanId);

    /**
     * 延时双删 看板缓存
     * @param kanbanId 看板
     */
    void doubleDelayedDeleteKanbanCache(Long kanbanId);

    /**
     * 获取看板缓存
     * @param kanbanId 看板
     * @return 缓存VO
     */
    List<ColumnVO> getKanbanCache(Long kanbanId);

    /**
     * 是否正在协作，同时把当前用户加入协作
     * @param kanbanId 看板
     * @param userId 用户
     * @return 是否正在协作
     */
    boolean isCooperating(Long kanbanId, Long userId);

}
