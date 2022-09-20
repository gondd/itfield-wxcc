package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.UserAccount;
import cn.itfield.wxcc.mapper.UserAccountMapper;
import cn.itfield.wxcc.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
