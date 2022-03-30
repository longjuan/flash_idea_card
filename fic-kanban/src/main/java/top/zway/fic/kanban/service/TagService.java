package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.ao.TagAO;

/**
 * @author ZZJ
 */
public interface TagService {
    /**
     * 新增
     * @param tagAo tag对象
     * @return 是否成功
     */
    boolean insertTag(TagAO tagAo);

    /**
     * 删除
     * @param tagId 要删除的
     * @param userid 用户id
     * @return 是否成功
     */
    boolean deleteTag(Long tagId, Long userid);
}
