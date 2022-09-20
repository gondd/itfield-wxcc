package cn.itfield.wxcc.service;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.result.JsonResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录表 服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
public interface ILoginService extends IService<Login> {

    JsonResult common(LoginDto loginDto);
}
