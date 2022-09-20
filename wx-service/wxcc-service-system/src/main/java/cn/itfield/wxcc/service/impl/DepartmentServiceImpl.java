package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Department;
import cn.itfield.wxcc.mapper.DepartmentMapper;
import cn.itfield.wxcc.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-06
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
