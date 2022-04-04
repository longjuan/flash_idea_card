package top.zway.fic.kanban.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.zway.fic.base.entity.vo.InvitationVO;
import top.zway.fic.base.result.R;
import top.zway.fic.kanban.service.InvitationService;
import top.zway.fic.web.holder.LoginUserHolder;

import java.util.List;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation")
@Api("邀请controller")
public class InvitationController {
    private final InvitationService invitationService;
    private final LoginUserHolder loginUserHolder;

    @PostMapping("")
    @ApiOperation("发送邀请")
    public R sendInvitation(@RequestParam("kanbanId") Long kanbanId, @RequestParam("invitedUser") String invitedUser) {
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = invitationService.invite(invitedUser, kanbanId, id);
        return R.judge(success, "邮箱不存在或无权限邀请");
    }

    @GetMapping("")
    @ApiOperation("获取邀请")
    public R getInvitation() {
        Long id = loginUserHolder.getCurrentUser().getId();
        List<InvitationVO> invitation = invitationService.getInvitation(id);
        return R.success(invitation);
    }

    @PutMapping("")
    @ApiOperation("同意邀请")
    public R accept(@RequestParam("invitationId") Long invitationId){
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean accept = invitationService.accept(invitationId, id);
        return R.judge(accept, "加入失败，看板可能已被删除");
    }
}
