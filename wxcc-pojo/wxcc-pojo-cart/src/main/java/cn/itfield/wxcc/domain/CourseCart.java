package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_course_cart")
public class CourseCart extends Model<CourseCart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String courseName;

    private String coursePic;

    /**
     * 价格冗余
     */
    private BigDecimal coursePrice;

    private Integer count;

    private Date createTime=new Date();

    private Date updateTime;

    /**
     * 状态：正常，删除
     */
    private Integer status=0;

    /**
     * 备注
     */
    private String courseNote;

    private Long courseId;

    /**
     * 用户ID
     */
    private Long loginId;

    private String gradeName;

    private BigDecimal oldCoursePrice;

}
