package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.ITokenService;
import cn.itfield.wxcc.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service
public class ITokenServiceImpl implements ITokenService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public JsonResult createToken(Long courseId, Long loginId) {
        Assert.isTrue(courseId!=null&&loginId!=null,"数据不能为空");
        String token = StrUtils.getRandomString(8);
        System.out.println(token);
        redisTemplate.opsForValue().set(token,courseId+"_"+loginId,30, TimeUnit.MINUTES);
        return JsonResult.me().setData(token);
    }
}
