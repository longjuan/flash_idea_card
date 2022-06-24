package top.zway.fic.kanban.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.base.result.R;
import top.zway.fic.kanban.service.GuideInitService;

/**
 * @author ZZJ
 */
@RestController
@Api("rpc初始化引导看板api")
@RequiredArgsConstructor
public class GuideInitRpcController {
    private final GuideInitService guideInitService;

    @GetMapping("/rpc/guide/init")
    @ApiOperation("rpc初始化引导看板")
    public R<String> initGuide(@RequestParam("userId") Long userId) {
        guideInitService.initGuide(userId);
        return R.success("初始化成功");
    }

}
