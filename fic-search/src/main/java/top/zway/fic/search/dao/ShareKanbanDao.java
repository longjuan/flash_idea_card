package top.zway.fic.search.dao;

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
     * 查用户有权限的看板id
     * @param userId 用户
     * @return 看板id
     */
    List<Long> listKanbanIdByUserId(Long userId);

}
