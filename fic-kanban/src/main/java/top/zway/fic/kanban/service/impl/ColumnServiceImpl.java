package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zway.fic.base.entity.ao.KanbanColumnAO;
import top.zway.fic.base.entity.doo.KanbanColumnDO;
import top.zway.fic.kanban.alg.MoveItemAlg;
import top.zway.fic.kanban.dao.CardDao;
import top.zway.fic.kanban.dao.ColumnDao;
import top.zway.fic.kanban.dao.ShareKanbanDao;
import top.zway.fic.kanban.dao.TagDao;
import top.zway.fic.kanban.service.ColumnService;
import top.zway.fic.web.exception.BizException;

import java.util.List;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {
    private final ColumnDao columnDao;
    private final ShareKanbanDao shareKanbanDao;
    private final CardDao cardDao;
    private final TagDao tagDao;

    private boolean isNoAuthorityByKanbanId(Long kanbanId, Long userId) {
        int access = shareKanbanDao.countHaveJoinedUser(kanbanId, userId);
        return access < 1;
    }

    private boolean isNoAuthorityByColumnId(Long columnId, Long userId) {
        Long kanbanId = columnDao.getKanbanIdByColumnId(columnId);
        return isNoAuthorityByKanbanId(kanbanId, userId);
    }

    @Override
    public boolean insertColumn(KanbanColumnAO kanbanColumnAo) {
        // 校验权限
        if (isNoAuthorityByKanbanId(kanbanColumnAo.getKanbanId(), kanbanColumnAo.getUpdateUser())) {
            return false;
        }
        // 获取最大的顺序
        Double lastOrder = columnDao.getLastOrder(kanbanColumnAo.getKanbanId());
        // Do对象
        KanbanColumnDO kanbanColumnDO = new KanbanColumnDO(null, null,
                kanbanColumnAo.getColumnTitle(), kanbanColumnAo.getKanbanId(), kanbanColumnAo.getUpdateUser(),
                null, null);
        kanbanColumnDO.setColumnOrder(lastOrder == null ? 1 : lastOrder + 1);
        // 插入
        int insert = columnDao.insert(kanbanColumnDO);
        return insert > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteColumn(Long columnId, Long userId) {
        // 校验权限
        if (isNoAuthorityByColumnId(columnId, userId)) {
            return false;
        }
        // 删除所有下属tag
        List<Long> cardIds = cardDao.listCardIdByColumnId(columnId);
        for (Long cardId : cardIds) {
            tagDao.deleteByCardId(cardId);
        }
        // 删除卡片
        cardDao.deleteByColumnId(columnId);
        // 删除列
        int delete = columnDao.delete(columnId);
        return delete > 0;
    }

    @Override
    public boolean updateColumn(KanbanColumnAO kanbanColumnAo) {
        // 校验权限
        if (isNoAuthorityByColumnId(kanbanColumnAo.getColumnId(), kanbanColumnAo.getUpdateUser())) {
            return false;
        }
        KanbanColumnDO kanbanColumnDO = new KanbanColumnDO(kanbanColumnAo.getColumnId(), null,
                kanbanColumnAo.getColumnTitle(), null, kanbanColumnAo.getUpdateUser(), null, null);
        int updateBaseInfo = columnDao.updateBaseInfo(kanbanColumnDO);
        return updateBaseInfo > 0;
    }

    @Override
    public boolean moveColumn(int move, Long columnId, Long userId) {
        // 校验权限
        Long kanbanId = columnDao.getKanbanIdByColumnId(columnId);
        if (isNoAuthorityByKanbanId(kanbanId, userId)) {
            return false;
        }
        if (move == 0) {
            throw new BizException("移动位置不能为0");
        }
        boolean down = move > 0;
        int getSize = Math.abs(move) + 2;
        // 获取上下的顺序
        List<Double> orders = columnDao.getSortedOrderAfterOrBefore(columnId, kanbanId, getSize, down);
        // 计算新顺序
        Double newOrder = MoveItemAlg.countNewOrder(orders, getSize, down);
        // 更新
        int update = columnDao.setOrder(newOrder, columnId);
        return update > 0;
    }
}
