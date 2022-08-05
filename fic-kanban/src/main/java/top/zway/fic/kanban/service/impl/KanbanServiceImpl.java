package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zway.fic.base.entity.ao.KanbanAO;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.base.entity.doo.*;
import top.zway.fic.base.entity.vo.CardVO;
import top.zway.fic.base.entity.vo.ColumnVO;
import top.zway.fic.base.entity.vo.KanbanContentVO;
import top.zway.fic.base.entity.vo.KanbanHomeVO;
import top.zway.fic.base.result.R;
import top.zway.fic.kanban.dao.*;
import top.zway.fic.kanban.rpc.UserRpcService;
import top.zway.fic.kanban.service.CacheService;
import top.zway.fic.kanban.service.KanbanService;
import top.zway.fic.kanban.service.SearchUpdateService;

import java.util.*;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KanbanServiceImpl implements KanbanService {
    private final KanbanDao kanbanDao;
    private final ShareKanbanDao shareKanbanDao;
    private final ColumnDao columnDao;
    private final CardDao cardDao;
    private final TagDao tagDao;
    private final UserRpcService userRpcService;
    private final CacheService cacheService;
    private final SearchUpdateService searchUpdateService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertKanban(KanbanAO kanbanAo) {
        KanbanDO kanbanDO = new KanbanDO(null, kanbanAo.getOwnerId(), kanbanAo.getTitle(), null,
                kanbanAo.getColor(), kanbanAo.getType(), null);
        // 插入看板信息
        int kanbanInsert = kanbanDao.insert(kanbanDO);
        if (kanbanInsert < 1) {
            return false;
        }
        // 插入授权记录
        ShareKanbanDO record = new ShareKanbanDO(kanbanDO.getKanbanId(), kanbanDO.getOwnerId(), false, null);
        int insert = shareKanbanDao.insert(record);
        searchUpdateService.update(new SearchUpdateBO(record.getKanbanId(), SearchUpdateBO.UpdateTypeEnum.KANBAN, record.getKanbanId()));
        return insert > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteKanban(Long kanbanId, Long userId) {
        // 鉴权
        KanbanDO kanbanDO = kanbanDao.selectByPrimaryKey(kanbanId);
        if (kanbanDO == null || kanbanDO.getOwnerId().longValue() != userId.longValue()) {
            return false;
        }
        // 删除tag
        List<Long> cardIds = cardDao.listCardIdByKanbanId(kanbanId);
        for (Long cardId : cardIds) {
            tagDao.deleteByCardId(cardId);
        }
        // 删除卡片
        cardDao.deleteByKanbanId(kanbanId);
        // 删除列
        columnDao.deleteByKanbanId(kanbanId);
        // 删除共享信息
        shareKanbanDao.deleteByKanbanId(kanbanId);
        // 删除看板
        int delete = kanbanDao.deleteByPrimaryKey(kanbanId);
        searchUpdateService.update(new SearchUpdateBO(kanbanId, SearchUpdateBO.UpdateTypeEnum.KANBAN, kanbanId));
        return delete > 0;
    }

    @Override
    public boolean updateKanban(KanbanAO kanbanAo) {
        // 鉴权
        KanbanDO role = kanbanDao.selectByPrimaryKey(kanbanAo.getKanbanId());
        if (role == null || role.getOwnerId().longValue() != kanbanAo.getOwnerId().longValue()) {
            return false;
        }
        // 插入
        KanbanDO record = new KanbanDO(kanbanAo.getKanbanId(), kanbanAo.getOwnerId(), kanbanAo.getTitle(),
                null, kanbanAo.getColor(), null, null);
        int update = kanbanDao.updateBaseInfo(record);
        // 搜索更新
        searchUpdateService.update(new SearchUpdateBO(kanbanAo.getKanbanId(), SearchUpdateBO.UpdateTypeEnum.KANBAN, kanbanAo.getKanbanId()));
        // 缓存刷新
        cacheService.doubleDelayedDeleteKanbanCache(kanbanAo.getKanbanId());
        return update > 0;
    }

    @Override
    public List<KanbanHomeVO> getMyKanbans(Long userId) {
        List<ShareKanbanDO> shareKanbanDoS = shareKanbanDao.selectByUserId(userId);
        Set<Long> userSet = new HashSet<>();
        for (ShareKanbanDO shareKanbanDo : shareKanbanDoS) {
            // 成员信息
            List<Long> users = shareKanbanDao.listUsersByKanbanId(shareKanbanDo.getKanbanId());
            userSet.addAll(users);
        }
        // rpc
        HashMap<Long, UserInfoDO> userInfoDos = userRpcService.getUserInfoDoByList(userSet.toArray(new Long[0])).getData();
        List<KanbanHomeVO> ret = new ArrayList<>(shareKanbanDoS.size());
        for (ShareKanbanDO shareKanbanDo : shareKanbanDoS) {
            // 看板信息
            KanbanDO kanbanDO = kanbanDao.selectByPrimaryKey(shareKanbanDo.getKanbanId());
            // user信息
            List<Long> users = shareKanbanDao.listUsersByKanbanId(shareKanbanDo.getKanbanId());
            List<UserInfoDO> menber = new ArrayList<>(users.size());
            for (Long user : users) {
                menber.add(userInfoDos.get(user));
            }
            // 封装
            KanbanHomeVO e = new KanbanHomeVO(kanbanDO, shareKanbanDo, menber);
            ret.add(e);
        }
        ret.sort((o1, o2) -> -(o1.getJoinTime().compareTo(o2.getJoinTime())));
        return ret;
    }

    @Override
    public KanbanContentVO getKanbanContent(Long userId, Long kanbanId) {
        // 鉴权
        ShareKanbanDO shareKanbanDO = shareKanbanDao.selectByKanbanIdAndUserId(kanbanId, userId);
        if (shareKanbanDO == null) {
            return null;
        }
        KanbanContentVO ret = new KanbanContentVO();
        // 协作
        boolean cooperating = cacheService.isCooperating(kanbanId, userId);
        ret.setCooperating(cooperating);
        // 看板基本信息
        KanbanDO kanbanDO = kanbanDao.selectByPrimaryKey(kanbanId);
        List<Long> userids = shareKanbanDao.listUsersByKanbanId(kanbanId);
        // rpc
        Collection<UserInfoDO> userInfoDos = userRpcService.getUserInfoDoByList(userids.toArray(new Long[0])).getData().values();

        KanbanHomeVO kanbanHomeVO = new KanbanHomeVO(kanbanDO, shareKanbanDO, new ArrayList<>(userInfoDos));
        ret.setBaseInfo(kanbanHomeVO);
        // 列信息
        List<ColumnVO> kanbanCache = cacheService.getKanbanCache(kanbanId);
        if (kanbanCache == null) {
            ArrayList<ColumnVO> columns = new ArrayList<>();
            List<KanbanColumnDO> kanbanColumnDoS = columnDao.selectByKanbanId(kanbanId);
            for (KanbanColumnDO kanbanColumnDo : kanbanColumnDoS) {
                // card
                List<CardDO> cardDoS = cardDao.selectByColumnIdOrdered(kanbanColumnDo.getColumnId());
                cardDoS.sort(Comparator.comparingDouble(CardDO::getOrderInColumn));
                List<CardVO> cardVOList = new ArrayList<>(cardDoS.size());
                for (CardDO cardDo : cardDoS) {
                    // 构造card vo
                    List<TagDO> tagDoS = null;
                    if (cardDo.getTagged()) {
                        tagDoS = tagDao.selectByCardId(cardDo.getCardId());
                    } else {
                        tagDoS = new ArrayList<>(0);
                    }
                    cardVOList.add(new CardVO(cardDo, tagDoS));
                }
                ColumnVO e = new ColumnVO(kanbanColumnDo, cardVOList);
                columns.add(e);
            }
            columns.sort(Comparator.comparingDouble(ColumnVO::getColumnOrder));
            ret.setColumns(columns);

            // 设置缓存
            cacheService.setKanbanCache(kanbanId, columns);
        } else {
            ret.setColumns(kanbanCache);
        }
        return ret;
    }
}
