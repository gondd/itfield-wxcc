package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-06
 */
@TableName("t_department")
public class Department extends Model<Department> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门编号
     */
    private String sn;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门的上级分类层级id
     */
    private String dirPath;

    /**
     * 部门状态，0正常，1禁用
     */
    private Integer state;

    /**
     * 部门管理员，关联Employee表id
     */
    private Long managerId;

    /**
     * 上级部门
     */
    private Long parentId;

    /**
     * 部门所属机构(租户)
     */
    private Long tenantId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Department{" +
        "id=" + id +
        ", sn=" + sn +
        ", name=" + name +
        ", dirPath=" + dirPath +
        ", state=" + state +
        ", managerId=" + managerId +
        ", parentId=" + parentId +
        ", tenantId=" + tenantId +
        "}";
    }
}
