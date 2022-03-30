package top.zway.fic.kanban.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.zway.fic.base.entity.ao.CardAO;
import top.zway.fic.base.entity.ao.KanbanColumnAO;
import top.zway.fic.base.entity.dto.CardDTO;
import top.zway.fic.base.entity.dto.KanbanColumnDTO;
import top.zway.fic.base.result.R;
import top.zway.fic.kanban.service.CardService;
import top.zway.fic.web.exception.Jsr303Checker;
import top.zway.fic.web.holder.LoginUserHolder;

import javax.validation.Valid;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban/card")
@Api("卡片controller")
public class CardController {
    private final LoginUserHolder loginUserHolder;
    private final CardService cardService;

    @PostMapping("")
    @ApiOperation("新增卡片")
    public R insertCard(@Valid @RequestBody CardDTO cardDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        Long id = loginUserHolder.getCurrentUser().getId();
        CardAO cardAo = new CardAO(cardDTO, id);
        boolean success = cardService.insertCard(cardAo);
        return R.judge(success, "无权限新增");
    }

    @DeleteMapping("")
    @ApiOperation("删除卡片")
    public R deleteCard(Long cardId) {
        if (cardId == null) {
            return R.failed("路径错误");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = cardService.deleteCard(cardId, id);
        return R.judge(success, "已被删除或无删除权限");
    }

    @PutMapping("")
    @ApiOperation("修改卡片信息")
    public R updateCard(@Valid @RequestBody CardDTO cardDTO, BindingResult bindingResult) {
        Jsr303Checker.check(bindingResult);
        Long id = loginUserHolder.getCurrentUser().getId();
        CardAO cardAo = new CardAO(cardDTO, id);
        boolean success = cardService.updateColumn(cardAo);
        return R.judge(success, "已被删除或无修改权限");
    }

    @PutMapping("/order")
    @ApiOperation("调整卡片顺序")
    public R moveCard(@RequestParam("move") Integer move, @RequestParam("cardId") Long cardId) {
        if (move == null || cardId == 0 || move == 0) {
            return R.failed("请求参数错误");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = cardService.moveColumn(move, cardId, id);
        return R.judge(success, "移动失败");
    }

    @PutMapping("/transfer")
    @ApiOperation("卡片流转")
    public R transferCard(@RequestParam("cardId") Long cardId, @RequestParam("columnId") Long columnId) {
        if (cardId == null || columnId == 0) {
            return R.failed("请求参数错误");
        }
        Long id = loginUserHolder.getCurrentUser().getId();
        boolean success = cardService.transferCard(cardId, columnId, id);
        return R.judge(success, "移动失败");
    }



}
