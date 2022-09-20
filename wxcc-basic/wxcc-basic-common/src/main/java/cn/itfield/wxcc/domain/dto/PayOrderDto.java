package cn.itfield.wxcc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderDto {
    /**
     * 支付金额
     */
    private BigDecimal amount;//支付金额
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * id
     */
    private String relationId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 扩展参数
     */
    private String extParams;
    /**
     * 订单描述
     */
    private String subject;
    /**
     * 购买人id
     */
    private Long userId;
    private Integer state;

    public PayOrderDto(BigDecimal amount, Integer payType, String relationId, String orderNo, String extParams, String subject, Long userId) {
        this.amount = amount;
        this.payType = payType;
        this.relationId = relationId;
        this.orderNo = orderNo;
        this.extParams = extParams;
        this.subject = subject;
        this.userId = userId;
    }
}
