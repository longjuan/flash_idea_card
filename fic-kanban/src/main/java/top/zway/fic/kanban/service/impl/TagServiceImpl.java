package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zway.fic.base.entity.ao.TagAO;
import top.zway.fic.base.entity.doo.TagDO;
import top.zway.fic.kanban.dao.CardDao;
import top.zway.fic.kanban.dao.ShareKanbanDao;
import top.zway.fic.kanban.dao.TagDao;
import top.zway.fic.kanban.service.CacheService;
import top.zway.fic.kanban.service.TagService;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final ShareKanbanDao shareKanbanDao;
    private final CardDao cardDao;
    private final CacheService cacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertTag(TagAO tagAo) {
        // 检查card与看板关系
        Long kanbanId = cardDao.getKanbanIdByCardId(tagAo.getCardId());
        if (kanbanId == null || kanbanId.longValue() != tagAo.getKanbanId().longValue()) {
            return false;
        }
        // 鉴权看板
        int countHaveJoinedUser = shareKanbanDao.countHaveJoinedUser(kanbanId, tagAo.getCreateUser());
        if (countHaveJoinedUser <= 0) {
            return false;
        }
        // 插入
        TagDO tagDO = new TagDO(null, tagAo.getCardId(), tagAo.getType(), tagAo.getColor(), tagAo.getContent(),
                tagAo.getKanbanId(), tagAo.getCreateUser(), null);
        int insert = tagDao.insert(tagDO);
        // 标记状态
        cardDao.updateTaggedState(tagAo.getCardId(), true);
        // 更新缓存
        cacheService.doubleDelayedDeleteKanbanCache(kanbanId);
        return insert > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTag(Long tagId, Long userid) {
        // 鉴权
        Long cardId = tagDao.getCardIdByTagId(tagId);
        if (cardId == null) {
            return false;
        }
        Long kanbanId = cardDao.getKanbanIdByCardId(cardId);
        if (kanbanId == null) {
            return false;
        }
        int countHaveJoinedUser = shareKanbanDao.countHaveJoinedUser(kanbanId, userid);
        if (countHaveJoinedUser <= 0) {
            return false;
        }
        // 删除
        int delete = tagDao.delete(tagId);
        // 标记新状态
        int tagNum = tagDao.countTagNumByCardId(cardId);
        if (tagNum == 0) {
            cardDao.updateTaggedState(cardId, false);
        }
        // 更新缓存
        cacheService.doubleDelayedDeleteKanbanCache(kanbanId);
        return delete > 0;
    }
}
