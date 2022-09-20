package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Data
@TableName("t_course_chapter")
public class CourseChapter extends Model<CourseChapter> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 章节名称
     */
    private String name;

    /**
     * 第几章
     */
    private Integer number;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名
     */
    private String courseName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<MediaFile> mediaFiles =new ArrayList<>();

}
