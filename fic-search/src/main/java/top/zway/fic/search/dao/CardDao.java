package top.zway.fic.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.CardDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface CardDao {

    /**
     * 根据主键查
     * @param cardId 主键
     * @return 卡片
     */
    CardDO selectByCardId(Long cardId);

    /**
     * 根据看板id查
     * @param kanbanId 看板
     * @return 卡片s
     */
    List<CardDO> selectByKanbanId(Long kanbanId);
}
