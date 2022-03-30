package top.zway.fic.base.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zway.fic.base.constant.PojoValidConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO implements Serializable {
    private Long cardId;

    private Long columnId;

    // 记得columnId和kanbanId进行检查

    private Long kanbanId;

    @Size(max = PojoValidConstants.CARD_CONTEXT_MAX_LEN,
            message = "卡片内容长度最大为250")
    @NotBlank(message = "卡片内容不能为空")
    private String content;
}
