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
public class ResourceRoleDO implements Serializable {
    private String resourceUrl;
    private String role;
}
