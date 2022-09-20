package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.UserBaseInfo;
import cn.itfield.wxcc.mapper.UserBaseInfoMapper;
import cn.itfield.wxcc.service.IUserBaseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员基本信息 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class UserBaseInfoServiceImpl extends ServiceImpl<UserBaseInfoMapper, UserBaseInfo> implements IUserBaseInfoService {

}
