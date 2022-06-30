package top.zway.fic.auth.service;

import top.zway.fic.base.entity.vo.RsaKeyVO;

/**
 * @author ZZJ
 */
public interface AsymmetricEncryptionService {
    /**
     * 生成公钥和私钥
     * @return 公钥及uuid
     */
    RsaKeyVO generateKeyPair();

    /**
     * 用uuid解密内容
     * @param uuid uuid
     * @param content 内容
     * @param needDelete 是否删除私钥
     * @return 解密后的内容
     */
    String decrypt(String uuid, String content, boolean needDelete);
}
