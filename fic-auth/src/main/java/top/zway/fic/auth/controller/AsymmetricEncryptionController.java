package top.zway.fic.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.auth.service.AsymmetricEncryptionService;
import top.zway.fic.base.entity.vo.RsaKeyVO;
import top.zway.fic.base.result.R;

/**
 * @author ZZJ
 */
@RestController
@RequiredArgsConstructor
@Api("对称加密")
@Slf4j
public class AsymmetricEncryptionController {
    private final AsymmetricEncryptionService asymmetricEncryptionService;

    @ApiOperation("生成rsa密钥对，并返回公钥和uuid")
    @GetMapping("/oauth/rsa")
    public R<RsaKeyVO> generateKeyPair() {
        return R.success(asymmetricEncryptionService.generateKeyPair());
    }

    @ApiOperation("rpc，用uuid解密内容")
    @PostMapping("/rpc/rsa/decrypt")
    public String decrypt(@RequestParam("uuid") String uuid, @RequestParam("content") String content) {
        return asymmetricEncryptionService.decrypt(uuid, content);
    }
}
