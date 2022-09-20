package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Component
public interface CourseMapper extends BaseMapper<Course> {

    List<String> selectIds(List<Long> teacharIds);

    void insetteacher(@Param("teacharIds") List<Long> teacharIds, @Param("course_id") Long course_id);

    List<Long> selectbyTeacherid(Long courseId);

    void deletebycourseID(Long id);

    void updatacourses(@Param("status") Long status, @Param("courseIds") List<Long> courseIds);

    List<Teacher> selectbycourseId(Long courseId);
}
