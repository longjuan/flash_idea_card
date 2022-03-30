package top.zway.fic.base.entity.ao;

import lombok.*;
import top.zway.fic.base.entity.dto.KanbanColumnDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author ZZJ
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanColumnAO extends KanbanColumnDTO {
    private Long updateUser;

    public KanbanColumnAO(KanbanColumnDTO kanbanColumnDTO, Long updateUser) {
        super(kanbanColumnDTO.getColumnId(), kanbanColumnDTO.getColumnTitle(), kanbanColumnDTO.getKanbanId());
        this.updateUser = updateUser;
    }
}
