package top.zway.fic.base.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanContentVO {
    private KanbanHomeVO baseInfo;

    private List<ColumnVO> columns;
}
