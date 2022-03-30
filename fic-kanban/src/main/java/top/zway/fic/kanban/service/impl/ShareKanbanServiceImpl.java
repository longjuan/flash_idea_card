package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zway.fic.kanban.dao.ShareKanbanDao;
import top.zway.fic.kanban.service.ShareKanbanService;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class ShareKanbanServiceImpl implements ShareKanbanService {
    private final ShareKanbanDao shareKanbanDao;
    @Override
    public boolean updateCollectState(Long kanbanId, Long userId, Boolean collected) {
        return shareKanbanDao.updateCollectState(kanbanId, userId, collected) > 0;
    }
}
