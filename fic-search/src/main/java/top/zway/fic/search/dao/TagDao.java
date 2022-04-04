package top.zway.fic.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.TagDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface TagDao {
    /**
     * 根据主键查
     * @param tagId 主键
     * @return 标签
     */
    TagDO selectByPrimaryKey(Long tagId);

    /**
     * 根据看板查标签
     * @param kanbanId 看板
     * @return 标签
     */
    List<TagDO> selectByKanbanId(Long kanbanId);
}
