package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 老师表 Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Component
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<String> selectIds(List<Long> teacharIds);
}
