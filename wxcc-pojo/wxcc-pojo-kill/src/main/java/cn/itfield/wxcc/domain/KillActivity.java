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
 * @since 2022-08-26
 */
@Data
@TableName("t_kill_activity")
public class KillActivity extends Model<KillActivity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String timeStr;

    private Date beginTime;

    private Date endTime;

    /**
     * 0待发布，1已发布 ，2已取消
     */
    private Integer publishStatus;

    private Date publishTime;


}
