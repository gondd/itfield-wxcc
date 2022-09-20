package cn.itfield.wxcc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AlipayDto {
    private String orderNo;
    private Integer payType;
    //支付完成跳转地址
    private String callUrl;
    private BigDecimal amount;
    private String subject;
    private List<Long> courseId;
}
