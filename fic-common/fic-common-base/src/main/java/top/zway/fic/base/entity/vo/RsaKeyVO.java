package top.zway.fic.base.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsaKeyVO {
    private String publicKey;
    private String uuid;
}
