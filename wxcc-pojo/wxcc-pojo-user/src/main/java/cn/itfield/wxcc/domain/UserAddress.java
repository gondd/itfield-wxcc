package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 收货地址
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@TableName("t_user_address")
public class UserAddress extends Model<UserAddress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 登录用户
     */
    private Long userId;

    /**
     * 收货人
     */
    private String reciver;

    /**
     * 区域
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 全地址
     */
    private String fullAddress;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 备用手机号
     */
    private String phoneBack;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 是否默认
     */
    private Integer defaultAddress;


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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneBack() {
        return phoneBack;
    }

    public void setPhoneBack(String phoneBack) {
        this.phoneBack = phoneBack;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", userId=" + userId +
        ", reciver=" + reciver +
        ", areaCode=" + areaCode +
        ", address=" + address +
        ", fullAddress=" + fullAddress +
        ", phone=" + phone +
        ", phoneBack=" + phoneBack +
        ", tel=" + tel +
        ", postCode=" + postCode +
        ", email=" + email +
        ", defaultAddress=" + defaultAddress +
        "}";
    }
}
