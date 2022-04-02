package top.zway.fic.search.service;

import top.zway.fic.base.entity.vo.SearchVO;

import java.util.List;

/**
 * @author ZZJ
 */
public interface SearchService {
    /**
     * 搜索
     * @param userId 用户id
     * @param key 关键词
     * @return 结果
     */
    List<SearchVO> search(String key, Long userId);

    void fullUpdate(Long kanbanId);
}
