package top.zway.fic.base.entity.doo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanColumnDO implements Serializable {
    private Long columnId;

    // 为加快计算，使用double得精度消失问题可以忽略

    private Double columnOrder;

    private String columnTitle;

    private Long kanbanId;

    private Long updateUser;

    private Date createTime;

    private Date updateTime;
}