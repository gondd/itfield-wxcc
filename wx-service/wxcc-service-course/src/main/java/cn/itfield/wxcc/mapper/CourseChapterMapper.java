package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.CourseChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频 Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Component
public interface CourseChapterMapper extends BaseMapper<CourseChapter> {

}
