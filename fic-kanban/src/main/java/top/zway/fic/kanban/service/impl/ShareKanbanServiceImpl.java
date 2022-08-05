package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zway.fic.kanban.dao.KanbanDao;
import top.zway.fic.kanban.dao.ShareKanbanDao;
import top.zway.fic.kanban.service.ShareKanbanService;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class ShareKanbanServiceImpl implements ShareKanbanService {
    private final ShareKanbanDao shareKanbanDao;
    private final KanbanDao kanbanDao;
    @Override
    public boolean updateCollectState(Long kanbanId, Long userId, Boolean collected) {
        return shareKanbanDao.updateCollectState(kanbanId, userId, collected) > 0;
    }

    @Override
    public boolean deleteShare(Long kanbanId, Long userId, Long actionUserId) {
        if (userId.equals(actionUserId)){
            return shareKanbanDao.deleteShare(kanbanId, userId) > 0;
        }
        if (kanbanDao.selectByPrimaryKey(kanbanId).getOwnerId().equals(actionUserId)){
            return shareKanbanDao.deleteShare(kanbanId, userId) > 0;
        }
        return false;
    }
}
