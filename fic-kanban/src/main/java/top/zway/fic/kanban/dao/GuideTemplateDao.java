package top.zway.fic.kanban.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zway.fic.base.entity.doo.GuideTemplateDO;

import java.util.List;

/**
 * @author ZZJ
 */
@Mapper
public interface GuideTemplateDao {
    List<GuideTemplateDO> getAll();
}
