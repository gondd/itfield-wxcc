package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.LoginLog;
import cn.itfield.wxcc.mapper.LoginLogMapper;
import cn.itfield.wxcc.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录记录 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
