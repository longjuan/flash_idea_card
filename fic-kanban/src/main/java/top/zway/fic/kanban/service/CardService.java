package top.zway.fic.kanban.service;

import top.zway.fic.base.entity.ao.CardAO;

/**
 * @author ZZJ
 */
public interface CardService {
    /**
     * 新增
     * @param cardAo 卡片
     * @return 是否成功
     */
    boolean insertCard(CardAO cardAo);

    /**
     * 删除
     * @param cardId 卡片id
     * @param userId 用户id
     * @return 是否成功
     */
    boolean deleteCard(Long cardId, Long userId);

    /**
     * 修改
     * @param cardAo 卡片
     * @return 是否成功
     */
    boolean updateColumn(CardAO cardAo);

    /**
     * 移动卡片顺序
     * @param move 移动相对位置
     * @param cardId 卡片id
     * @param userId 用户id
     * @return 是否成功
     */
    boolean moveColumn(Integer move, Long cardId, Long userId);

    /**
     * 流转到其他列
     * @param cardId 卡片
     * @param columnId 列
     * @param userId 用户id
     * @return 是否成功
     */
    boolean transferCard(Long cardId, Long columnId, Long userId);
}
