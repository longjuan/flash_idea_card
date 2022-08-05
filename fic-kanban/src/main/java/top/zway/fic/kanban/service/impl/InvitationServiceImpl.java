package top.zway.fic.kanban.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zway.fic.base.constant.RabbitMqConstants;
import top.zway.fic.base.entity.bo.InvitationReminderBO;
import top.zway.fic.base.entity.doo.InvitationDO;
import top.zway.fic.base.entity.doo.KanbanDO;
import top.zway.fic.base.entity.doo.ShareKanbanDO;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.base.entity.vo.InvitationVO;
import top.zway.fic.base.result.R;
import top.zway.fic.kanban.dao.InvitationDao;
import top.zway.fic.kanban.dao.KanbanDao;
import top.zway.fic.kanban.dao.ShareKanbanDao;
import top.zway.fic.kanban.rpc.UserRpcService;
import top.zway.fic.kanban.service.InvitationService;

import java.util.*;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final UserRpcService userRpcService;
    private final InvitationDao invitationDao;
    private final KanbanDao kanbanDao;
    private final ShareKanbanDao shareKanbanDao;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public boolean invite(String invitedUser, Long kanbanId, Long sendUser) {
        // 鉴权
        KanbanDO kanbanDO = kanbanDao.selectByPrimaryKey(kanbanId);
        if (kanbanDO == null) {
            return false;
        }
        if (!kanbanDO.getOwnerId().equals(sendUser)) {
            return false;
        }
        // rpc找id
        HashMap<String, Long> map = userRpcService.getUserIdByUsername(new String[]{invitedUser}).getData();
        Long invitedUserId = map.get(invitedUser);
        if (invitedUserId == null) {
            return false;
        }
        List<Long> userIds = shareKanbanDao.listUsersByKanbanId(kanbanId);
        for (Long userId : userIds) {
            if (userId.equals(invitedUserId)) {
                return false;
            }
        }
        // 插入
        int insert = invitationDao.insert(new InvitationDO(null, invitedUserId, sendUser, kanbanId, null, 1));
        // 邮件提醒
        R<HashMap<Long, UserInfoDO>> userInfoDo = userRpcService.getUserInfoDoByList(new Long[]{sendUser});
        String nickname = userInfoDo.getData().get(sendUser).getNickname();
        InvitationReminderBO invitationReminderBO = new InvitationReminderBO(nickname, invitedUser, kanbanDO.getTitle());
        rabbitTemplate.convertAndSend(RabbitMqConstants.INVITATION_REMINDER_EXCHANGE_NAME, "", invitationReminderBO);
        return insert > 0;
    }

    @Override
    public List<InvitationVO> getInvitation(Long invitedUser) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        List<InvitationDO> invitationDOList = invitationDao.selectByInvitedUser(invitedUser, calendar.getTime());
        // rpc 用户信息
        Set<Long> userids = new HashSet<>();
        for (InvitationDO invitationDO : invitationDOList) {
            userids.add(invitationDO.getSendUser());
        }
        // 构造返回
        HashMap<Long, UserInfoDO> userinfoData = userRpcService.getUserInfoDoByList(userids.toArray(new Long[0])).getData();
        List<InvitationVO> ret = new ArrayList<>(invitationDOList.size());
        for (InvitationDO invitationDO : invitationDOList) {
            KanbanDO kanbanDO = kanbanDao.selectByPrimaryKey(invitationDO.getKanbanId());
            ret.add(new InvitationVO(invitationDO.getInvitationId(), invitationDO.getInvitedUser(),
                    userinfoData.get(invitationDO.getSendUser()), kanbanDO,
                    invitationDO.getInvitationTime(), invitationDO.getState()));
        }
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accept(Long invitationId, Long userid) {
        // 鉴权
        InvitationDO invitationDO = invitationDao.selectByInvitationId(invitationId);
        if (invitationDO == null || !invitationDO.getInvitedUser().equals(userid) || !invitationDO.getState().equals(1)) {
            return false;
        }
        // 看看还在不在
        KanbanDO kanbanDO = kanbanDao.selectByPrimaryKey(invitationDO.getKanbanId());
        if (kanbanDO == null) {
            return false;
        }
        invitationDao.updateState(userid, invitationDO.getKanbanId(), 2);
        shareKanbanDao.insert(new ShareKanbanDO(invitationDO.getKanbanId(), userid, Boolean.FALSE, null));
        return true;
    }

    @Override
    public boolean reject(Long invitationId, Long userid) {
        // 鉴权
        InvitationDO invitationDO = invitationDao.selectByInvitationId(invitationId);
        if (invitationDO == null || !invitationDO.getInvitedUser().equals(userid) || !invitationDO.getState().equals(1)) {
            return false;
        }
        invitationDao.selectByInvitationId(invitationId);
        return true;
    }
}
