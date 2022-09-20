package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Role;
import cn.itfield.wxcc.mapper.RoleMapper;
import cn.itfield.wxcc.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
