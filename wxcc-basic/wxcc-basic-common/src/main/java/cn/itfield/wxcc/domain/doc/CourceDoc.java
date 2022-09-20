package cn.itfield.wxcc.domain.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Data
@Document(indexName = "wxcc",type = "course")
public class CourceDoc {
    @Id
    private Long id;

    /**
     * 课程名称
     */
    @Field(type = FieldType.Text,store = true,analyzer = "ik_max_word",index = true)
    private String name;

    /**
     * 适用人群
     */
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String forUser;

    /**
     * 课程分类
     */
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String courseTypeName;
    @Field(type = FieldType.Long,store = true,index = true)
    private Long courseTypeId;
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String gradeName;

    /**
     * 课程等级
     */
    @Field(type = FieldType.Long,store = true,index = true)
    private Long gradeId;

    /**
     * 课程状态，下线：0 ， 上线：1
     */
    @Field(type = FieldType.Integer,store = true,index = true)
    private Integer status=0;

    /**
     * 添加课程的后台用户的ID
     */

    @Field(type = FieldType.Integer,index = false,store = true)
    private Integer totalMinute = 0;

    @Field(type = FieldType.Integer,index = true,store = true)
    private Integer charge;
    @Field(type = FieldType.Double,index = true,store = true)
    private BigDecimal price;
    @Field(type = FieldType.Double,index = true,store = true)
    private BigDecimal priceOld;
    @Field(type = FieldType.Integer,index = true,store = true)
    private Integer saleCount;
    @Field(type = FieldType.Integer,index = true,store = true)
    private Integer viewCount;
    @Field(type = FieldType.Integer,index = true,store = true)
    private Integer commentCount;
    /**
     * 添加课程的后台用户
     */

    /**
     * 课程的开课时间
     */
    @Field(type = FieldType.Long,store = true,index = true)
    private Long startTime;

    /**
     * 课程的节课时间
     */
    @Field(type = FieldType.Long,store = true,index = true)
    private Long endTime;

    /**
     * 封面，云存储地址
     */
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String pic;

    /**
     * 时长，以分钟为单位
     */

    /**
     * 上线时间
     */
    @Field(type = FieldType.Long,store = true,index = true)
    private Long onlineTime;

    /**
     * 章节数
     */
    @Field(type = FieldType.Integer,store = true,index = true)
    private Integer chapterCount;

    /**
     * 讲师，逗号分隔多个
     */
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String teacherNames;
}
