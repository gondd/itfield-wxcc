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
 * 会员登录账号
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Data
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Date createTime=new Date();
    @TableField("update_time")
    private Date updateTime;

    /**
     * 三方登录名
     */
    @TableField("third_uid")
    private String thirdUid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户状态
     */
    @TableField("bit_state")
    private Long bitState=8l;

    /**
     * 安全级别
     */
    @TableField("sec_level")
    private Integer secLevel=1;
    @TableField("login_id")
    private Long loginId;



}
