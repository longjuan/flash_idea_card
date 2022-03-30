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
public class KanbanColumnDTO implements Serializable {
    private Long columnId;

    @Size(max = PojoValidConstants.COLUMN_TITLE_MAX_LEN,
            message = "标题最大长度为30")
    @NotBlank(message = "标题不能为空")
    private String columnTitle;

    private Long kanbanId;
}
