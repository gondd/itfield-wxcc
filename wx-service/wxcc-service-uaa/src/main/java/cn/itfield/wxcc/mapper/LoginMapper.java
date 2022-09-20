package cn.itfield.wxcc.mapper;

import cn.itfield.wxcc.domain.Login;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 登录表 Mapper 接口
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
public interface LoginMapper extends BaseMapper<Login> {

    Login selectbyusername(String username);

}
