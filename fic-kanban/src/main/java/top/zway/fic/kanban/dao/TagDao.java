package top.zway.fic.kanban.dao;

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
     * 新增 带写入key
     * @param record tag
     * @return 数量
     */
    int insert(TagDO record);

    /**
     * 根据标签id找卡片id
     * @param tagId 标签i
     * @return 卡片id 可能为null
     */
    Long getCardIdByTagId(@Param("tagId") Long tagId);

    /**
     * 删除
     * @param tagId 标签id
     * @return 数量
     */
    int delete(@Param("tagId") Long tagId);

    /**
     * 删除卡片下的所有标签
     * @param cardId 卡片
     * @return 数量
     */
    int deleteByCardId(@Param("cardId") Long cardId);

    /**
     * 统计卡片下标签数量
     * @param cardId 卡片
     * @return 标签数量
     */
    int countTagNumByCardId(@Param("cardId") Long cardId);

    /**
     *
     * @param cardId
     * @return
     */
    List<TagDO> selectByCardId(Long cardId);
}
