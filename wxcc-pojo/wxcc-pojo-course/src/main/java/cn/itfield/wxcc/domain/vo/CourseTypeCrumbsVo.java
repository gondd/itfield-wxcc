package cn.itfield.wxcc.domain.vo;

import cn.itfield.wxcc.domain.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTypeCrumbsVo {
    //当前分类
    private CourseType current;
    //当前分类的同级分类列表
    private List<CourseType> brothers;
}