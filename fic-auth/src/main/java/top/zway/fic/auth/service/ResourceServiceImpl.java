package top.zway.fic.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.zway.fic.auth.dao.UserAuthDao;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.doo.ResourceRoleDO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 把角色权限放进redis
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserAuthDao userAuthDao;

    @PostConstruct
    public void initData() {
        List<ResourceRoleDO> resourceRoleDos = userAuthDao.listResourceRole();
        Map<String, List<String>> resourceRolesMap = new HashMap<>(resourceRoleDos.size() / 2);
        for (ResourceRoleDO resourceRoleDo : resourceRoleDos) {
            List<String> roleList = resourceRolesMap.getOrDefault(resourceRoleDo.getResourceUrl(), new ArrayList<>());
            roleList.add(resourceRoleDo.getRole());
            resourceRolesMap.put(resourceRoleDo.getResourceUrl(), roleList);
        }
        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}