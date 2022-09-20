package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-26
 */
@Data
@TableName("t_kill_course")
public class KillCourse extends Model<KillCourse> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 对应的课程ID
     */
    private Long courseId;

    private BigDecimal killPrice;

    /**
     * 库存
     */
    private Integer killCount;

    /**
     * 每个人可以秒杀的数量,默认1
     */
    private Integer killLimit=1;

    /**
     * 秒杀课程排序
     */
    private Integer killSort;

    /**
     * 秒杀状态:0待发布，1秒杀中，2秒杀结束
     */
    private Integer publishStatus=0;

    private String coursePic;

    /**
     * 秒杀开始时间
     */
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 发布到Redis的时间
     */
    private Date publishTime;

    /**
     * 老师，用逗号隔开
     */
    private String teacherNames;

    /**
     * 下线时间
     */
    private Date offlineTime;

    private Long activityId;

    private String timeStr;
    @TableField(exist = false)
    private String killstates;

}
