package top.zway.fic.base.entity.vo;

import lombok.*;
import top.zway.fic.base.entity.doo.CardDO;
import top.zway.fic.base.entity.doo.TagDO;

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
public class CardVO extends CardDO {
    private Integer realCardOrder;

    private List<TagDO> tags;

    public CardVO(CardDO cardDO, Integer realCardOrder, List<TagDO> tags) {
        super(cardDO.getCardId(), cardDO.getOrderInColumn(), cardDO.getUpdateTime(), cardDO.getColumnId(),
                cardDO.getKanbanId(), cardDO.getContent(), cardDO.getTagged(), cardDO.getUpdateUser(), cardDO.getCreateTime());
        this.realCardOrder = realCardOrder;
        this.tags = tags;
    }
}
