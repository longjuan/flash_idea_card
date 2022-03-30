package top.zway.fic.kanban.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.KanbanDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface KanbanDao {

    /**
     * 新增 带写入key
     * @param record 看板
     * @return 数量
     */
    int insert(KanbanDO record);

    /**
     * 获取看板信息
     * @param kanbanId 看板id
     * @return 看板信息
     */
    KanbanDO selectByPrimaryKey(Long kanbanId);

    /**
     * 删除
     * @param kanbanId 看板id
     * @return 数量
     */
    int deleteByPrimaryKey(@Param("kanbanId") Long kanbanId);

    /**
     * 修改
     * @param record 看板
     * @return 数量
     */
    int updateBaseInfo(KanbanDO record);

}
