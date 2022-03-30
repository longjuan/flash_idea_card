package top.zway.fic.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zway.fic.base.entity.doo.ResourceRoleDO;
import top.zway.fic.base.entity.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * @author ZZJ
 */
@Mapper
public interface UserAuthDao {
    /**
     * 根据用户名出UserDTO
     * @param username 用户名
     * @return UserDTO列表
     */
    List<UserDTO> listUserDtoByUsername(@Param("username") String username);

    /**
     * 所有url的
     * @return 所有url的
     */
    List<ResourceRoleDO> listResourceRole();
}
