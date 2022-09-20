package cn.itfield.wxcc.domain.dto;

import cn.itfield.wxcc.domain.CourseOrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class CourseOrderDto {
    private String payType;
    private Long type;
    private String token;
    private List<Long> courseIds;
    private Long userId;
    List<CourseOrderItem> courseOrderItems;
    private BigDecimal totalAmount;
    private String orderNo;
}
