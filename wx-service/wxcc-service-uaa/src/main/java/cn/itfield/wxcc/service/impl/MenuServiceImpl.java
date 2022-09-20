package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.Menu;
import cn.itfield.wxcc.mapper.MenuMapper;
import cn.itfield.wxcc.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
