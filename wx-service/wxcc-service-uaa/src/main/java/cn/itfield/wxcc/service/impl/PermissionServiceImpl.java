package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Permission;
import cn.itfield.wxcc.mapper.PermissionMapper;
import cn.itfield.wxcc.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
