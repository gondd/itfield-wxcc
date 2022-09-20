package cn.itfield.wxcc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
@Data
@TableName("t_course_order")
public class CourseOrder extends Model<CourseOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 最后支付更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付总的价格
     */
    private BigDecimal totalAmount;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 数量
     */
    private Integer totalCount;

    /**
     * 订单状态：0下单成功待支付，1已支付，2订单取消，3订单退款，4支付超时或失败，5订单确认订单完成
     */
    private Integer statusOrder=0;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 订单标题
     */
    private String title;

    /**
     * 支付方式:0余额直接，1支付宝，2微信,3银联
     */
    private Integer payType=1;

    /**
     * 0普通订单 ， 1秒杀订单
     */
    private Integer orderType;
    @TableField(exist = false)
    private List<CourseOrderItem> courseOrderItems;


}
