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
 * @since 2022-08-21
 */
@Data
@TableName("t_pay_order")
public class PayOrder extends Model<PayOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流水创建时间
     */
    private Date createTime=new Date();

    private Date updateTime;

    /**
     * 发生金额
     */
    private BigDecimal amount;

    /**
     * 支付方式:0余额直接，1支付宝，2银联,3微信
     */
    private Integer payType=1;

    /**
     * 业务ID，可以关联订单ID,或者课程ID
     */
    private String relationId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 购买者用户id
     */
    private Long userId;

    /**
     * 扩展参数，格式： xx=1&oo=2
     */
    private String extParams;

    /**
     * 订单描述
     */
    private String subject;

    /**
     * 支付状态：0下单成功待支付，1已支付，2取消支付，3订单退款，4支付超时或失败，5订单确认订单完成
     */
    private Integer payStatus=0;
    //状态 7天自动确认就是2,超时未支付就是1
    @TableField(exist = false)
    private Integer state;


}
