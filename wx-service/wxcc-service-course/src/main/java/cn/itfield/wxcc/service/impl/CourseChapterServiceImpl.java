package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.CourseChapter;
import cn.itfield.wxcc.mapper.CourseChapterMapper;
import cn.itfield.wxcc.mapper.CourseMapper;
import cn.itfield.wxcc.service.ICourseChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements ICourseChapterService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    @Transactional
    public boolean save(CourseChapter entity) {
        Integer count = baseMapper.selectCount(new QueryWrapper<CourseChapter>().eq("course_id", entity.getCourseId()));
        System.out.println(entity);

        entity.setNumber(count+1);
        Course course = courseMapper.selectById(entity.getCourseId());
        course.setChapterCount(count+1);
        courseMapper.updateById(course);
        return super.save(entity);
    }
}
