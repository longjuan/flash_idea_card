package top.zway.fic.base.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUpdateBO implements Serializable {
    private Long kanbanId;
    private UpdateTypeEnum updateType;
    private Long data;

    public enum UpdateTypeEnum {
        KANBAN,
        COLUMN,
        CARD,
        TAG;
    }
}
