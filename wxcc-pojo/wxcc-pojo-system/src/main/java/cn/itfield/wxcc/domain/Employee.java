package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-06
 */
@TableName("t_employee")
public class Employee extends Model<Employee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^[\u4e00-\u9fa5]{2,}$",message ="请输入中文姓名" )
    private String realName;

    /**
     * 电话
     */
    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1[3-9]{1}[0-9]{9}$",message = "请输入正确的手机号")
    private String tel;

    /**
     * 邮箱
     */
    @Email(message = "请输入正确的邮件")
    @NotBlank(message = "邮件不能为空")
    private String email;

    /**
     * 创建时间
     */
    private LocalDate inputTime;

    /**
     * 状态：0正常，1锁定，2注销
     */
    private Integer state;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 员工类型 ， 1平台普通员工 ，2平台客服人员，3平台管理员，4机构员工，5,机构管理员或其他
     */
    private Boolean type;

    private Long loginId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDate inputTime) {
        this.inputTime = inputTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Employee{" +
        "id=" + id +
        ", realName=" + realName +
        ", tel=" + tel +
        ", email=" + email +
        ", inputTime=" + inputTime +
        ", state=" + state +
        ", deptId=" + deptId +
        ", type=" + type +
        ", loginId=" + loginId +
        "}";
    }
}
