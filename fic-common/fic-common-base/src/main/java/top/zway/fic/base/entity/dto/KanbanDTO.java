package top.zway.fic.base.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import top.zway.fic.base.constant.PojoValidConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanDTO implements Serializable {
    private Long kanbanId;

    @Size(max = PojoValidConstants.KANBAN_TITLE_MAX_LEN,
            message = "看板名称最大为80")
    @NotBlank(message = "看板名称不能为空")
    private String title;

    @Pattern(regexp = "^#?([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$",
            message = "颜色错误")
    private String color;

    @Range(min = PojoValidConstants.KANBAN_TYPE_MIN_LEN,
            max = PojoValidConstants.KANBAN_TYPE_MAX_LEN,
            message = "看板类型错误")
    private Integer type;
}
