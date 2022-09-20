package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 登录记录
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@TableName("t_login_log")
public class LoginLog extends Model<LoginLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    private Long loginId;

    /**
     * IP
     */
    private String ip;

    /**
     * 客户端
     */
    private String clientInfo;

    /**
     * 登录方式
     */
    private Integer loginType;

    /**
     * 登录是否成功
     */
    private Integer success;

    /**
     * 结果说明
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
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
        return "LoginLog{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", loginId=" + loginId +
        ", ip=" + ip +
        ", clientInfo=" + clientInfo +
        ", loginType=" + loginType +
        ", success=" + success +
        ", remark=" + remark +
        "}";
    }
}
