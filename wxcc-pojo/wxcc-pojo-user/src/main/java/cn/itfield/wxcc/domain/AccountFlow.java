package cn.itfield.wxcc.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@TableName("t_account_flow")
public class AccountFlow extends Model<AccountFlow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流水金额
     */
    private BigDecimal amount;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 流水描述
     */
    private String remark;

    private LocalDateTime createTime;

    /**
     * 流水类型：充值，提现，购买课程
     */
    private Integer businessType;

    private String businessName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AccountFlow{" +
        "id=" + id +
        ", amount=" + amount +
        ", accountId=" + accountId +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", businessType=" + businessType +
        ", businessName=" + businessName +
        "}";
    }
}
