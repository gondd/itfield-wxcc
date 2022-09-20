package cn.itfield.wxcc.service;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员登录账号 服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
public interface IUserService extends IService<User> {

    void register(LoginDto loginDto);

    List<User> getuser(List<Long> userids);
}
