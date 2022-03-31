package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.vo.ColumnVO;

import java.util.List;

/**
 * @author ZZJ
 */
public interface CacheService {

    void setKanbanCache(Long kanbanId, List<ColumnVO> columns);

    void deleteKanbanCache(Long kanbanId);

    void doubleDelayedDeleteKanbanCache(Long kanbanId);

    List<ColumnVO> getKanbanCache(Long kanbanId);

    boolean isCooperating(Long kanbanId, Long userId);

}
