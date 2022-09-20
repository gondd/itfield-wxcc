package cn.itfield.wxcc.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PreCreatedOrderDto {
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

    /**
     * 小计金额
     */
    private BigDecimal amount;

    /**
     * 课程价格
     */
    private BigDecimal price;

    /**
     * 课程的数量
     */
    private Integer count;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 封面
     */
    private String coursePic;


    /**
     * 秒杀课程ID
     */
    private Long killCourseId;
}
