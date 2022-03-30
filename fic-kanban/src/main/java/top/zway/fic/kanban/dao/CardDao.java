package top.zway.fic.kanban.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.CardDO;
import top.zway.fic.base.entity.doo.KanbanColumnDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface CardDao {
    /**
     * 新增 带写入key
     * @param record 卡片
     * @return 数量
     */
    int insert(CardDO record);

    /**
     * 获取列最后一个顺序
     * @param columnId 列id
     * @return 顺序 可能为null
     */
    Double getLastOrder(@Param("columnId") Long columnId);

    /**
     * 根据卡片id找看板id
     * @param cardId 卡片id
     * @return 看板id 可能为null
     */
    Long getKanbanIdByCardId(@Param("cardId") Long cardId);

    /**
     * 删除
     * @param cardId 卡片id
     * @return 数量
     */
    int delete(@Param("cardId") Long cardId);

    /**
     * 列下所有卡片id
     * @param columnId 列
     * @return 卡片id
     */
    List<Long> listCardIdByColumnId(@Param("columnId") Long columnId);

    /**
     * 删除列下所有卡片
     * @param columnId 列
     * @return 数量
     */
    int deleteByColumnId(@Param("columnId") Long columnId);

    /**
     * 列出看板下所有卡片id
     * @param kanbanId 看板
     * @return 卡片id
     */
    List<Long> listCardIdByKanbanId(@Param("kanbanId") Long kanbanId);

    /**
     * 删除看板下所有卡片
     * @param kanbanId 看板
     * @return 数量
     */
    int deleteByKanbanId(@Param("kanbanId") Long kanbanId);

    /**
     * 修改
     * @param record 看板
     * @return 数量
     */
    int updateBaseInfo(CardDO record);

    /**
     * 获取之前或之后排序后的顺序表（包含自己）
     * 例如：
     *     数据库顺序 1.0  2.9  2.9  2.9  4.6  5.9  6.0
     *     cardId对应顺序2.9
     *     getSize 4
     *     down true 返回 2.9 2.9 2.9 4.6
     *     down false 返回 2.9 1.0
     *
     * @param cardId 卡片id
     * @param columnId 针对的列
     * @param getSize  包含自己要多少个
     * @param down     升序
     * @return 顺序
     */
    List<Double> getSortedOrderAfterOrBefore(@Param("cardId") Long cardId, @Param("columnId") Long columnId,
                                             @Param("getSize") int getSize, @Param("down") boolean down);

    /**
     * 根据卡片id查列id
     * @param cardId 卡片id
     * @return 列id
     */
    Long getColumnIdByCardId(Long cardId);

    /**
     * 设置顺序
     * @param order 顺序
     * @param cardId 卡片id
     * @return 数量
     */
    int setOrder(@Param("order") Double order, @Param("cardId") Long cardId);

    /**
     * 转移卡片列
     * @param cardId 卡片
     * @param order 新列中顺序
     * @param culumnId 新列id
     * @return 数量
     */
    int transferCard(@Param("cardId") Long cardId, @Param("order") Double order, @Param("columnId") Long culumnId);

    /**
     * 改变tag状态
     * @param cardId 卡片
     * @param tagged 状态
     * @return 数量
     */
    int updateTaggedState(@Param("cardId") Long cardId, @Param("tagged") boolean tagged);

    /**
     * 根据列id查
     * @param ColumnId lie
     * @return 卡片s
     */
    List<CardDO> selectByColumnIdOrdered(Long ColumnId);
}
