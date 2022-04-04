package top.zway.fic.base.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zway.fic.base.entity.doo.KanbanDO;
import top.zway.fic.base.entity.doo.UserInfoDO;

import java.util.Date;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationVO {
    private Long invitationId;
    private Long invitedUser;
    private UserInfoDO sendUser;
    private KanbanDO kanbanId;
    private Date invitationTime;
    private Integer state;
}
