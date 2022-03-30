package top.zway.fic.base.entity.ao;

import lombok.*;
import top.zway.fic.base.entity.dto.TagDTO;

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
public class TagAO extends TagDTO {
    private Long createUser;
    private Integer type;

    public TagAO(TagDTO tagDTO, Long createUser, Integer type) {
        super(tagDTO.getCardId(), tagDTO.getColor(), tagDTO.getContent(), tagDTO.getKanbanId());
        this.createUser = createUser;
        this.type = type;
    }
}
