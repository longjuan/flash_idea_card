package top.zway.fic.auth.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zway.fic.auth.service.AsymmetricEncryptionService;
import top.zway.fic.base.constant.RedisConstant;
import top.zway.fic.base.entity.vo.RsaKeyVO;
import top.zway.fic.redis.util.RedisUtils;

/**
 * @author ZZJ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AsymmetricEncryptionServiceImpl implements AsymmetricEncryptionService {
    private final RedisUtils redisUtils;

    @Override
    public RsaKeyVO generateKeyPair() {
        RSA rsa = new RSA();
        String uuid = IdUtil.simpleUUID();
        redisUtils.set(RedisConstant.RSA_PRIVATE_KEY + uuid, rsa.getPrivateKeyBase64(), RedisConstant.RSA_PUBLIC_KEY_EXP_TIME);
        return new RsaKeyVO(rsa.getPublicKeyBase64(), uuid);
    }

    @Override
    public String decrypt(String uuid, String content) {
        try {
            Object obj = redisUtils.get(RedisConstant.RSA_PRIVATE_KEY + uuid);
            redisUtils.del(RedisConstant.RSA_PRIVATE_KEY + uuid);
            if (obj instanceof String) {
                String privateKey = (String) obj;
                RSA rsa = new RSA(privateKey, null);
                return StrUtil.str(rsa.decrypt(content, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            }
        }catch (Exception e){
            log.info("解密失败，uuid：{}，异常信息：{}", uuid, e.getMessage());
        }
        return null;
    }
}
