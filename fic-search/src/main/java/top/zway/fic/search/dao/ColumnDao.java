package top.zway.fic.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.KanbanColumnDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface ColumnDao {
    /**
     * 根据主键查
     * @param columnId 主键
     * @return 列
     */
    KanbanColumnDO selectByPrimaryKey(Long columnId);

    /**
     * 根据看板id查
     * @param kanbanId 看板id
     * @return 列
     */
    List<KanbanColumnDO> selectByKanbanId(Long kanbanId);
}
