package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> bymenu(Long id);
}
