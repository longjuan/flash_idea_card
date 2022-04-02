package top.zway.fic.search.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.zway.fic.search.entity.ContentInfoIndexEsDO;

/**
 * @author ZZJ
 */
@Repository
public interface ContentInfoIndexEsDao extends ElasticsearchRepository<ContentInfoIndexEsDO, String> {

}
