package top.zway.fic.base.entity.doo;

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
public class GuideTemplateDO implements Serializable {
    private Integer index;
    private String type;
    private String content;
    private String color;
}
