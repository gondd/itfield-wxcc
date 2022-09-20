package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.UserAddress;
import cn.itfield.wxcc.mapper.UserAddressMapper;
import cn.itfield.wxcc.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

}
