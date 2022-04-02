package top.zway.fic.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "kanban_content")
@Component
public class ContentInfoIndexEsDO {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long kanbanId;
    @Field(type = FieldType.Long)
    private Long columnId;
    @Field(type = FieldType.Long)
    private Long cardId;
    @Field(type = FieldType.Long)
    private Long tagId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
}
