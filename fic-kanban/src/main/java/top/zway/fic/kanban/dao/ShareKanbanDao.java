package top.zway.fic.kanban.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.ShareKanbanDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface ShareKanbanDao {

    /**
     * 新增
     *
     * @param record ShareKanbanDO
     * @return 数量
     */
    int insert(ShareKanbanDO record);

    /**
     * 看板指定用户计数
     *
     * @param kanbanId 看板
     * @param userId   指定用户
     * @return 计数
     */
    int countHaveJoinedUser(@Param("kanbanId") Long kanbanId, @Param("userId") Long userId);

    /**
     * 删除看板的共享信息
     *
     * @param kanbanId 看板
     * @return 计数
     */
    int deleteByKanbanId(@Param("kanbanId") Long kanbanId);

    /**
     * 查出用户的所有有权限的看板
     *
     * @param userId 用户
     * @return 所有有权限的看板
     */
    List<ShareKanbanDO> selectByUserId(@Param("userId") Long userId);


    /**
     * 通过看板id查有权限的用户
     *
     * @param kanbanId 看板
     * @return 用户id
     */
    List<Long> listUsersByKanbanId(@Param("kanbanId") Long kanbanId);

    /**
     * 根据用户和看板id查看板收藏等信息
     * @param userId 用户
     * @param kanbanId 看板id
     * @return 看板共享
     */
    ShareKanbanDO selectByKanbanIdAndUserId(@Param("kanbanId") Long kanbanId, @Param("userId") Long userId);

    /**
     * 改变收藏状态
     * @param kanbanId 看板
     * @param userId 用户
     * @param collected 收藏状态
     * @return 数量
     */
    int updateCollectState(@Param("kanbanId") Long kanbanId, @Param("userId") Long userId, @Param("collected") Boolean collected);

    /**
     * 退出协作
     * @param kanbanId 看板
     * @param userId 用户
     * @return 数量
     */
    int deleteShare(@Param("kanbanId")Long kanbanId, @Param("userId")Long userId);
}
