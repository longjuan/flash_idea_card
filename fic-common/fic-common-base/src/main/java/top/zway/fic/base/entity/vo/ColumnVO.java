package top.zway.fic.base.entity.vo;

import lombok.*;
import top.zway.fic.base.entity.doo.KanbanColumnDO;

import java.util.Date;
import java.util.List;

/**
 * @author ZZJ
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnVO extends KanbanColumnDO {
    private List<CardVO> cards;

    public ColumnVO(KanbanColumnDO kanbanColumnDO, List<CardVO> cards) {
        super(kanbanColumnDO.getColumnId(), kanbanColumnDO.getColumnOrder(), kanbanColumnDO.getColumnTitle(),
                kanbanColumnDO.getKanbanId(), kanbanColumnDO.getUpdateUser(), kanbanColumnDO.getCreateTime(),
                kanbanColumnDO.getUpdateTime());
        this.cards = cards;
    }
}
