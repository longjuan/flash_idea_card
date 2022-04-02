package top.zway.fic.search.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.base.entity.doo.KanbanDO;
import top.zway.fic.base.entity.doo.TagDO;
import top.zway.fic.redis.util.RedisUtils;
import top.zway.fic.search.dao.CardDao;
import top.zway.fic.search.dao.ContentInfoIndexEsDao;
import top.zway.fic.search.dao.KanbanDao;
import top.zway.fic.search.dao.TagDao;
import top.zway.fic.search.entity.ContentInfoIndexEsDO;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class TagPartialUpdateHandler implements PartialUpdateStrategyContext.IPartialUpdateStrategy {
    private final TagDao tagDao;
    private final ContentInfoIndexEsDao contentInfoIndexEsDao;

    public static final String ID_PREFIX = "tag_";

    @Override
    public void handlePartialUpdate(SearchUpdateBO searchUpdateBO) {
        TagDO tagDO = tagDao.selectByPrimaryKey(searchUpdateBO.getData());
        if (tagDO == null) {
            // 删除es
            contentInfoIndexEsDao.deleteById(ID_PREFIX + searchUpdateBO.getData());
        } else {
            // 更新es
            ContentInfoIndexEsDO e = new ContentInfoIndexEsDO(ID_PREFIX + searchUpdateBO.getData(),
                    tagDO.getKanbanId(), null, tagDO.getCardId(), tagDO.getTagId(), tagDO.getContent());
            contentInfoIndexEsDao.save(e);
        }
    }

    @Override
    public SearchUpdateBO.UpdateTypeEnum getType() {
        return SearchUpdateBO.UpdateTypeEnum.TAG;
    }
}
