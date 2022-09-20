package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 成长值记录
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@TableName("t_user_grow_log")
public class UserGrowLog extends Model<UserGrowLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    /**
     * 登录用户
     */
    private Long userId;

    /**
     * 来源
     */
    private String fromReason;

    /**
     * 成长值
     */
    private Integer score;

    /**
     * 备注
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFromReason() {
        return fromReason;
    }

    public void setFromReason(String fromReason) {
        this.fromReason = fromReason;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserGrowLog{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", userId=" + userId +
        ", fromReason=" + fromReason +
        ", score=" + score +
        ", remark=" + remark +
        "}";
    }
}
