package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zway.fic.base.constant.KanbanConstants;
import top.zway.fic.base.entity.doo.*;
import top.zway.fic.kanban.dao.*;
import top.zway.fic.kanban.service.GuideInitService;

import java.util.List;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class GuideInitServiceImpl implements GuideInitService {
    private final KanbanDao kanbanDao;
    private final ColumnDao columnDao;
    private final CardDao cardDao;
    private final TagDao tagDao;
    private final ShareKanbanDao shareKanbanDao;

    private final GuideTemplateDao guideTemplateDao;

    @Override
    public void initGuide(Long userId) {
        // 拿到模板
        List<GuideTemplateDO> all = guideTemplateDao.getAll();
        Long kanbanId = null;
        Long columnId = null;
        Long cardId = null;
        for (GuideTemplateDO guideTemplateDO : all) {
            // 执行模板
            if ("kanban".equals(guideTemplateDO.getType())) {
                KanbanDO kanbanDO = new KanbanDO(null, userId, guideTemplateDO.getContent(), null,
                        guideTemplateDO.getColor(), KanbanConstants.GUIDE_KANBAN_TYPE, null);
                kanbanDao.insert(kanbanDO);
                kanbanId = kanbanDO.getKanbanId();
                shareKanbanDao.insert(new ShareKanbanDO(kanbanId, userId, false, null));
            } else if ("column".equals(guideTemplateDO.getType())) {
                KanbanColumnDO columnDO = new KanbanColumnDO(null, guideTemplateDO.getIndex() * 10.1,
                        guideTemplateDO.getContent(), kanbanId, userId, null, null);
                columnDao.insert(columnDO);
                columnId = columnDO.getColumnId();
            } else if ("card".equals(guideTemplateDO.getType())) {
                CardDO cardDO = new CardDO(null, guideTemplateDO.getIndex() * 10.1, null,
                        columnId, kanbanId, guideTemplateDO.getContent(), true, userId, null);
                cardDao.insert(cardDO);
                cardId = cardDO.getCardId();
            } else if ("tag".equals(guideTemplateDO.getType())) {
                TagDO tagDO = new TagDO(null, cardId, KanbanConstants.KANBAN_TAG_TYPE_DEFAULT,
                        guideTemplateDO.getColor(), guideTemplateDO.getContent(), kanbanId, userId, null);
                tagDao.insert(tagDO);
            }
        }
    }
}
