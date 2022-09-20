package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.CourseDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
public interface ICourseService extends IService<Course> {

    void save(CourseDto courseDto);

    void updateById(CourseDto courseDto);

    Map<String, Object> gettimetable(Long courseId);

    void onLineCourse(List<Long> courseIds);

    void offLineCourse( List<Long> courseids);

    Map<String, Object> detaildata(Long courseId);

    List<Course> courseorder(List<Long> courseIds);
}
