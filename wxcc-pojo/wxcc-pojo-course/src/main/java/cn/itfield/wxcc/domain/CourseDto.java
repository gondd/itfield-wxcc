package cn.itfield.wxcc.domain;

import lombok.Data;

import java.util.List;

@Data
public class CourseDto {
    private Course course;
    private CourseDetail courseDetail;
    private CourseMarket courseMarket;
    private CourseResource courseResource;
    private List<Long> teacharIds;
}
