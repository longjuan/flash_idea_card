package top.zway.fic.base.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zway.fic.base.constant.PojoValidConstants;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO implements Serializable {
    private Long cardId;

    @Pattern(regexp = "^#?([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$",
            message = "颜色错误")
    private String color;

    @Size(max = PojoValidConstants.TAG_CONTEXT_MAX_LEN,
            message = "标签内容最大为15")
    private String content;

    // 看板id与用户在看板表是可信的，再去查看板与card关系是否存在

    private Long kanbanId;
}
