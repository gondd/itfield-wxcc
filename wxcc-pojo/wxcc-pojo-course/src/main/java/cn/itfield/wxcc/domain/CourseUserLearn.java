package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

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
@TableName("t_course_user_learn")
public class CourseUserLearn extends Model<CourseUserLearn> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long loginId;

    private Date startTime=new Date();

    private Date endTime=new Date();

    /**
     * 0已购买，1已过期
     */
    private Integer state;

    private Long courseId;

    private String courseOrderNo;

    private Date createTime=new Date();


}
