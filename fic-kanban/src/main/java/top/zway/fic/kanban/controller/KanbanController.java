package top.zway.fic.kanban.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.zway.fic.base.entity.ao.KanbanAO;
import top.zway.fic.base.entity.dto.KanbanDTO;
import top.zway.fic.base.entity.vo.KanbanContentVO;
import top.zway.fic.base.entity.vo.KanbanHomeVO;
import top.zway.fic.base.result.R;
import top.zway.fic.kanban.service.KanbanService;
import top.zway.fic.kanban.service.ShareKanbanService;
import top.zway.fic.web.exception.Jsr303Checker;
import top.zway.fic.web.holder.LoginUserHolder;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban")
@Api("看板controller")
public class KanbanController {
    private final LoginUserHolder loginUserHolder;
    private final KanbanService kanbanService;
    private final ShareKanbanService shareKanbanService;

    @PostMapping("")
    @ApiOperation("新增看板")
    public R insertKanban(@Valid @RequestBody KanbanDTO kanbanDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        Long id = loginUserHolder.getCurrentUser().getId();
        KanbanAO kanbanAo = new KanbanAO(kanbanDTO, id);
        boolean success = kanbanService.insertKanban(kanbanAo);
        return R.judge(success);
    }

    @DeleteMapping("")
    @ApiOperation("删除看板")
    public R deleteKanban(Long kanbanId) {
        if (kanbanId == null) {
            return R.failed("路径错误");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = kanbanService.deleteKanban(kanbanId, id);
        return R.judge(success, "已被删除或无删除权限");
    }

    @PutMapping("")
    @ApiOperation("修改看板信息")
    public R updateKanban(@Valid @RequestBody KanbanDTO kanbanDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        Long id = loginUserHolder.getCurrentUser().getId();
        KanbanAO kanbanAo = new KanbanAO(kanbanDTO, id);
        boolean success = kanbanService.updateKanban(kanbanAo);
        return R.judge(success, "已被删除或无修改权限");
    }

    @GetMapping("")
    @ApiOperation("获取看板")
    public R<List<KanbanHomeVO>> getMyKanbans(){
        Long id = loginUserHolder.getCurrentUser().getId();
        List<KanbanHomeVO> myKanbans = kanbanService.getMyKanbans(id);
        return R.success(myKanbans);
    }

    @GetMapping("/content")
    @ApiOperation("看板内容")
    public R<KanbanContentVO> getKanbanContent(@RequestParam("kanbanId") Long kanbanId){
        Long id = loginUserHolder.getCurrentUser().getId();
        KanbanContentVO kanbanContent = kanbanService.getKanbanContent(id, kanbanId);
        return R.success(kanbanContent);
    }

    @PostMapping("/collect")
    @ApiOperation("收藏/取消收藏")
    public R collect(@RequestParam("kanbanId") Long kanbanId,@RequestParam("isCollected") Boolean isCollected){
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = shareKanbanService.updateCollectState(kanbanId, id, isCollected);
        return R.judge(success);
    }

    @DeleteMapping("/share")
    @ApiOperation("退出协作")
    public R deleteShare(@RequestParam("kanbanId") Long kanbanId, @RequestParam("userId") Long userId){
        Long actionUserId = loginUserHolder.getCurrentUser().getId();
        boolean success = shareKanbanService.deleteShare(kanbanId, userId, actionUserId);
        return R.judge(success);
    }
}
