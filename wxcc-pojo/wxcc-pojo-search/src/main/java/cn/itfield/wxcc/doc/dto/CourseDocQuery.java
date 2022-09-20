package cn.itfield.wxcc.doc.dto;

import cn.itfield.wxcc.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDocQuery extends BaseQuery {
    //根据课程类别id搜索
    private Long courseTypeId;
    //根据课程类别名称搜索
    private String courseTypeName;
    //根据课程等级id搜索
    private String gradeId;
    //根据课程等级名称搜索
    private String gradeName;
    //根据价格范围搜索
    private BigDecimal priceMin;
    //根据价格范围搜索
    private BigDecimal priceMax;

}
