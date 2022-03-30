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
public class CardDO implements Serializable {
    private Long cardId;

    // 为加快计算，使用double得精度消失问题可以忽略

    private Double orderInColumn;

    private Date updateTime;

    private Long columnId;

    private Long kanbanId;

    private String content;

    private Boolean tagged;

    private Long updateUser;

    private Date createTime;
}