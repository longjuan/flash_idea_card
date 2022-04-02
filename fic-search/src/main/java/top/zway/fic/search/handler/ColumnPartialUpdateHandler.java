package top.zway.fic.search.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.base.entity.doo.KanbanColumnDO;
import top.zway.fic.redis.util.RedisUtils;
import top.zway.fic.search.dao.ColumnDao;
import top.zway.fic.search.dao.ContentInfoIndexEsDao;
import top.zway.fic.search.entity.ContentInfoIndexEsDO;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class ColumnPartialUpdateHandler implements PartialUpdateStrategyContext.IPartialUpdateStrategy {
    private final ColumnDao columnDao;
    private final ContentInfoIndexEsDao contentInfoIndexEsDao;

    public static final String ID_PREFIX = "column_";

    @Override
    public void handlePartialUpdate(SearchUpdateBO searchUpdateBO) {
        KanbanColumnDO kanbanColumnDO = columnDao.selectByPrimaryKey(searchUpdateBO.getData());
        if (kanbanColumnDO == null) {
            // 删除es
            contentInfoIndexEsDao.deleteById(ID_PREFIX + searchUpdateBO.getData());
        } else {
            // 更新es
            ContentInfoIndexEsDO e = new ContentInfoIndexEsDO(ID_PREFIX + searchUpdateBO.getData(), kanbanColumnDO.getKanbanId(),
                    searchUpdateBO.getData(), null, null, kanbanColumnDO.getColumnTitle());
            contentInfoIndexEsDao.save(e);
        }
    }

    @Override
    public SearchUpdateBO.UpdateTypeEnum getType() {
        return SearchUpdateBO.UpdateTypeEnum.COLUMN;
    }
}
