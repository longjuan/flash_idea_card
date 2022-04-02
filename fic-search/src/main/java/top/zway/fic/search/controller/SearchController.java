package top.zway.fic.search.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.base.entity.bo.SearchUpdateBO;
import top.zway.fic.base.entity.vo.SearchVO;
import top.zway.fic.base.result.R;
import top.zway.fic.search.service.SearchService;
import top.zway.fic.web.holder.LoginUserHolder;

import java.util.List;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api("搜索controller")
public class SearchController {
    private final LoginUserHolder loginUserHolder;
    private final SearchService searchService;

    @GetMapping("/search")
    @ApiOperation("用户搜索")
    public R<List<SearchVO>> search(@RequestParam("key") String key) {
        Long id = loginUserHolder.getCurrentUser().getId();
        List<SearchVO> search = searchService.search(key, id);
        return R.success(search);
    }

    @GetMapping("/rpc/full/update")
    @ApiOperation("手动全量更新，不对外开发")
    public R fullUpdate(Long kanbanId) {
        long start = System.currentTimeMillis();
        searchService.fullUpdate(kanbanId);
        log.info("全量更新{}耗时{}ms", kanbanId, System.currentTimeMillis() - start);
        return R.success();
    }
}
