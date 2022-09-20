package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.client.LoginClient;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.domain.User;
import cn.itfield.wxcc.domain.UserAccount;
import cn.itfield.wxcc.domain.UserBaseInfo;
import cn.itfield.wxcc.mapper.UserAccountMapper;
import cn.itfield.wxcc.mapper.UserBaseInfoMapper;
import cn.itfield.wxcc.mapper.UserMapper;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private LoginClient loginClient;
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void register(LoginDto loginDto) {
        System.out.println(loginDto);
        Assert.isTrue(StringUtils.hasText(loginDto.getPhone()),"手机号不能为空!");
        Assert.isTrue(StringUtils.hasText(loginDto.getImageCodeKey()),"验证码key不能为空!");
        Assert.isTrue(StringUtils.hasText(loginDto.getImageCode()),"图形验证码不能为空!");
        Assert.isTrue(StringUtils.hasText(loginDto.getPassword()),"密码不能为空!");
        Assert.isTrue(StringUtils.hasText(loginDto.getSmsCode()),"短信验证码不能为空!");
        //验证图片验证码
        Object o = redisTemplate.opsForValue().get(loginDto.getImageCodeKey());
        Assert.isTrue(o!=null,"验证码已经过期");
        Assert.isTrue(loginDto.getImageCode().equals(o),"验证码错误");
        System.out.println(redisTemplate.opsForValue().get(loginDto.getPhone()));
        //验证短信验证码
        Assert.isTrue(redisTemplate.hasKey(loginDto.getPhone()),"短信验证码已经过期");
        Object o1 = redisTemplate.opsForValue().get(loginDto.getPhone());
        System.out.println(o1);
        Assert.isTrue(loginDto.getSmsCode().equals(o1),"短信验证码错误");

        //判断手机号存在
        User phone1 = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", loginDto.getPhone()));
        Assert.isTrue(phone1==null,"账号已存在");
        Login login = new Login();
        login.setUsername(loginDto.getPhone());
        login.setPassword(loginDto.getPassword());
        //保存login
        JsonResult jsonResult = loginClient.saveOrUpdate(login);
        Assert.isTrue(jsonResult.me().getSuccess(),"保存失败");
        long l = ((Integer) jsonResult.getData()).longValue();
        //保存user
        User user = new User();
        user.setPhone(loginDto.getPhone());
        user.setNickName(loginDto.getPhone());
        user.setLoginId(l);
        baseMapper.insert(user);
        //保存userinfo
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(user.getId());
        userBaseInfo.setRegChannel(loginDto.getRegChannel());
        userBaseInfoMapper.insert(userBaseInfo);
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(user.getId());
        userAccountMapper.insert(userAccount);
    }

    @Override
    public List<User> getuser(List<Long> userids) {
        System.out.println(userids);
        List<User> users = baseMapper.selectBatchIds(userids);

        return users;

    }
}
