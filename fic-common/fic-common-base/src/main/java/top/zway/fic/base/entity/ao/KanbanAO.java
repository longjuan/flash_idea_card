package top.zway.fic.base.entity.ao;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import top.zway.fic.base.entity.dto.KanbanDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author ZZJ
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanAO extends KanbanDTO {
    private Long ownerId;

    public KanbanAO(KanbanDTO kanbanDTO, Long ownerId) {
        super(kanbanDTO.getKanbanId(), kanbanDTO.getTitle(), kanbanDTO.getColor(), kanbanDTO.getType());
        this.ownerId = ownerId;
    }
}
