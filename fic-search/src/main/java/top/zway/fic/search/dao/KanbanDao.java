package top.zway.fic.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.KanbanDO;

/**
 * @author ZZJ
 */
@Mapper
public interface KanbanDao {
    /**
     * 获取看板信息
     * @param kanbanId 看板id
     * @return 看板信息
     */
    KanbanDO selectByPrimaryKey(Long kanbanId);

}
