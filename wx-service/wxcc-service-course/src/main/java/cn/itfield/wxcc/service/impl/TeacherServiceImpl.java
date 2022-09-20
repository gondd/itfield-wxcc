package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Teacher;
import cn.itfield.wxcc.mapper.TeacherMapper;
import cn.itfield.wxcc.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 老师表 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-10
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

}
