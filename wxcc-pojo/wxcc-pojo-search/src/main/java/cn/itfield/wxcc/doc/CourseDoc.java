/*
package cn.itfield.wxcc.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Data
@Document(indexName = "ymcc",type = "course")
public class CourseDoc {
    @Id
    private Long id;

    @Field(type = FieldType.Text,index = true,store = true,analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword,index = false,store = true)
    private String pic;

    */
/**
     * 适用人群
     *//*

    @Field(type = FieldType.Keyword,index = true,store = true)
    private String forUser;

    */
/**
     * 课程分类
     *//*

    @Field(type = FieldType.Keyword,index = true,store = true)
    private String courseTypeName;
    @Field(type = FieldType.Long,index = true,store = true)
    private Long courseTypeId;

    */
/**
     * 课程等级
     *//*

    @Field(type = FieldType.Long,index = true,store = true)
    private Long gradeId;
    @Field(type = FieldType.Keyword,index = true,store = true)
    private String gradeName;

    */
/**
     * 课程的开课时间
     * 提交表单报错：LocalDateTime有问题，由于前端提交的时间没有时分秒，只有年月日，不能用LocalDateTime，可以使用
     * LocalDate或者Date
     *//*

    @Field(type = FieldType.Long,index = true,store = true)
    private Long startTime;

    */
/**
     * 课程的节课时间
     *//*

    @Field(type = FieldType.Long,index = true,store = true)
    private Long endTime;

    */
/**
     * 时长，以分钟为单位
     *//*


    */
/**
     * 上线时间
     *//*

    @Field(type = FieldType.Long,index = true,store = true)
    private Long onlineTime;

    */
/**
     * 章节数
     *//*

    @Field(type = FieldType.Integer,index = false,store = true)
    private Integer chapterCount = 0;

    */
/**
     * 讲师，逗号分隔多个
     *//*

    @Field(type = FieldType.Keyword,index = true,store = true)
    private String teacherNames;
}
*/
