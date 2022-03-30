package top.zway.fic.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.RoleUserDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface RoleUserDao {

    /**
     * 分配用户角色
     * @param roleUserDoS 用户角色列表
     * @return 数量
     */
    int insertRoleUser(@Param("roleUserDoS") List<RoleUserDO> roleUserDoS);
}
