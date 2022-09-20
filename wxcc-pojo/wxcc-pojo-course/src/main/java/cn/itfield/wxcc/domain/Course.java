package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Data
@TableName("t_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 适用人群
     */
    private String forUser;

    /**
     * 课程分类
     */
    private Long courseTypeId;

    private String gradeName;

    /**
     * 课程等级
     */
    private Long gradeId;

    /**
     * 课程状态，下线：0 ， 上线：1
     */
    private Integer status;

    /**
     * 添加课程的后台用户的ID
     */
    private Long loginId;

    /**
     * 添加课程的后台用户
     */
    private String loginUserName;

    /**
     * 课程的开课时间
     */
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date startTime;

    /**
     * 课程的节课时间
     */
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date endTime;

    /**
     * 封面，云存储地址
     */
    private String pic;

    /**
     * 时长，以分钟为单位
     */
    private Integer totalMinute;

    /**
     * 上线时间
     */
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date onlineTime;

    /**
     * 章节数
     */
    private Integer chapterCount;

    /**
     * 讲师，逗号分隔多个
     */
    private String teacherNames;

    @TableField(exist = false)
    private BigDecimal price;
    @TableField(exist = false)
    private BigDecimal priceOld;

}
