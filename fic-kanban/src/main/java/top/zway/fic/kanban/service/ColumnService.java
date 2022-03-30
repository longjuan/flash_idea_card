package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.ao.KanbanColumnAO;

/**
 * @author ZZJ
 */
public interface ColumnService {
    /**
     * 插入列
     * @param kanbanColumnAo 列
     * @return 是否成功
     */
    boolean insertColumn(KanbanColumnAO kanbanColumnAo);

    /**
     * 删除列
     * @param columnId 列
     * @param userId 用户id
     * @return 是否成功
     */
    boolean deleteColumn(Long columnId, Long userId);

    /**
     * 修改列
     * @param kanbanColumnAo 列
     * @return 是否成功
     */
    boolean updateColumn(KanbanColumnAO kanbanColumnAo);

    /**
     * 移动列顺序
     * @param move 移动相对位置
     * @param columnId 列id
     * @param userId 用户id
     * @return 是否成功
     */
    boolean moveColumn(int move,Long columnId, Long userId);
}
