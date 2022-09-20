package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 商品收藏
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Data
@TableName("t_course_collect")
public class CourseCollect extends Model<CourseCollect> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Date createTime=new Date();

    /**
     * 登录用户
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long courseId;


}
