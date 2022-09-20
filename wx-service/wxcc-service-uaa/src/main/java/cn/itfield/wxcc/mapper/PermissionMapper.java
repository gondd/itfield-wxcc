package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> bypermission(Long id);
}
