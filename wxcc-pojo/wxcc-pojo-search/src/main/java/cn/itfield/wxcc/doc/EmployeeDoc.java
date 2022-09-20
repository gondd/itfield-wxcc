package cn.itfield.wxcc.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*@Document(indexName = "wxcc",type = "employee")*/
public class EmployeeDoc {
    @Id
    private Long id;
    /**
     * store默认不存储
     * type指分词
     */
    @Field(type = FieldType.Keyword,store = true)
    private String name;
    @Field(type = FieldType.Integer,store = true)
    private Integer age;
}
