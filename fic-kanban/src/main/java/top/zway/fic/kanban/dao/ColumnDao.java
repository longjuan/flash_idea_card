package top.zway.fic.kanban.dao;

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
     * 新增 带写入key
     *
     * @param record 列
     * @return 数量
     */
    int insert(KanbanColumnDO record);

    /**
     * 获取最后一个列顺序号
     *
     * @param kanbanId 看板
     * @return 最后一个列顺序号
     */
    Double getLastOrder(@Param("kanbanId") Long kanbanId);

    /**
     * 根据列id找看板id
     *
     * @param columnId 列id
     * @return 看板id 可能为null
     */
    Long getKanbanIdByColumnId(@Param("columnId") Long columnId);

    /**
     * 删除
     *
     * @param columnId 列
     * @return 数量
     */
    int delete(@Param("columnId") Long columnId);

    /**
     * 根据看板id删除
     *
     * @param kanbanId 看板id
     * @return 数量
     */
    int deleteByKanbanId(@Param("kanbanId") Long kanbanId);

    /**
     * 修改
     *
     * @param record 列
     * @return 数量
     */
    int updateBaseInfo(KanbanColumnDO record);

    /**
     * 获取之前或之后排序后的顺序表（包含自己）
     * 例如：
     *     数据库顺序 1.0  2.9  2.9  2.9  4.6  5.9  6.0
     *     columnId对应顺序2.9
     *     getSize 4
     *     down true 返回 2.9 2.9 2.9 4.6
     *     down false 返回 2.9 1.0
     *
     * @param columnId 针对的列
     * @param kanbanId 所在看板
     * @param getSize  包含自己要多少个
     * @param down     升序
     * @return 顺序
     */
    List<Double> getSortedOrderAfterOrBefore(@Param("columnId") Long columnId, @Param("kanbanId") Long kanbanId,
                                             @Param("getSize") int getSize, @Param("down") boolean down);

    /**
     * 设置顺序
     * @param order 顺序
     * @param columnId 列id
     * @return 数量
     */
    int setOrder(@Param("order") Double order, @Param("columnId") Long columnId);

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
