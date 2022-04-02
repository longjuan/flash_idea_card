package top.zway.fic.search.service.impl;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.base.entity.doo.CardDO;
import top.zway.fic.base.entity.doo.KanbanColumnDO;
import top.zway.fic.base.entity.doo.TagDO;
import top.zway.fic.base.entity.vo.SearchVO;
import top.zway.fic.redis.util.RedisUtils;
import top.zway.fic.search.config.SearchUpdateRabbitMqConfig;
import top.zway.fic.search.dao.*;
import top.zway.fic.search.entity.ContentInfoIndexEsDO;
import top.zway.fic.search.handler.CardPartialUpdateHandler;
import top.zway.fic.search.handler.ColumnPartialUpdateHandler;
import top.zway.fic.search.handler.KanbanPartialUpdateHandler;
import top.zway.fic.search.handler.TagPartialUpdateHandler;
import top.zway.fic.search.service.SearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ContentInfoIndexEsDao contentInfoIndexEsDao;
    private final ShareKanbanDao shareKanbanDao;
    private final ColumnDao columnDao;
    private final CardDao cardDao;
    private final TagDao tagDao;
    private final CardPartialUpdateHandler cardPartialUpdateHandler;
    private final ColumnPartialUpdateHandler columnPartialUpdateHandler;
    private final KanbanPartialUpdateHandler kanbanPartialUpdateHandler;
    private final TagPartialUpdateHandler tagPartialUpdateHandler;

    @Override
    public List<SearchVO> search(String key, Long userId) {
        long[] kanbanIds = shareKanbanDao.listKanbanIdByUserId(userId).stream().mapToLong(Long::longValue).toArray();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<QueryBuilder> must = boolQueryBuilder.must();
        must.add(QueryBuilders.termsQuery("kanbanId", kanbanIds));
        must.add(QueryBuilders.matchQuery("content", key));
        Iterable<ContentInfoIndexEsDO> result = contentInfoIndexEsDao.search(boolQueryBuilder);
        List<SearchVO> ret = new ArrayList<>();
        for (ContentInfoIndexEsDO contentInfoIndexEsDO : result) {
            String type = null;
            if (contentInfoIndexEsDO.getTagId() != null) {
                type = "tag";
            } else if (contentInfoIndexEsDO.getCardId() != null) {
                type = "card";
            } else if (contentInfoIndexEsDO.getColumnId() != null) {
                type = "column";
            } else {
                type = "kanban";
            }
            ret.add(new SearchVO(contentInfoIndexEsDO.getContent(), type, contentInfoIndexEsDO.getKanbanId()));
        }
        return ret;
    }

    @Override
    public void fullUpdate(Long kanbanId) {
        kanbanPartialUpdateHandler.handlePartialUpdate(new SearchUpdateBO(kanbanId, null, kanbanId));
        List<KanbanColumnDO> kanbanColumnDOS = columnDao.selectByKanbanId(kanbanId);
        for (KanbanColumnDO kanbanColumnDO : kanbanColumnDOS) {
            columnPartialUpdateHandler.handlePartialUpdate(new SearchUpdateBO(kanbanId, null,
                    kanbanColumnDO.getColumnId()));
        }
        List<CardDO> cardDOS = cardDao.selectByKanbanId(kanbanId);
        for (CardDO cardDO : cardDOS) {
            cardPartialUpdateHandler.handlePartialUpdate(new SearchUpdateBO(kanbanId, null, cardDO.getCardId()));
        }
        List<TagDO> tagDOS = tagDao.selectByKanbanId(kanbanId);
        for (TagDO tagDO : tagDOS) {
            tagPartialUpdateHandler.handlePartialUpdate(new SearchUpdateBO(kanbanId, null, tagDO.getTagId()));
        }

    }
}
