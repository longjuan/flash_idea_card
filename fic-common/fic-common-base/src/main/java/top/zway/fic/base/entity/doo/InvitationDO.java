package top.zway.fic.base.entity.doo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDO {
    private Long invitationId;
    private Long invitedUser;
    private Long sendUser;
    private Long kanbanId;
    private Date invitationTime;
    private Integer state;
}
