package cn.itfield.wxcc.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.ICommonService;
import cn.itfield.wxcc.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
@Service
@Slf4j
public class ICommonServiceImpl implements ICommonService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public JsonResult verifycode(String imageCodeKey) {
        System.out.println(imageCodeKey);
        //非空判断
        Assert.isTrue(StringUtils.hasText(imageCodeKey),"验证码key不能为空！");
        //用hutool生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 100, 4, 20);
        String code = captcha.getCode();
        redisTemplate.opsForValue().set(imageCodeKey,code,3, TimeUnit.MINUTES);
        String imageBase64 = captcha.getImageBase64();

        return JsonResult.me().setData(imageBase64);
    }

    @Override
    public void sendSmsCode(LoginDto loginDto) {
        Assert.isTrue(StringUtils.hasText(loginDto.getPhone()),"手机号不能为空");
        Assert.isTrue(StringUtils.hasText(loginDto.getImageCode()),"验证码不能为空");
        Assert.isTrue(StringUtils.hasText(loginDto.getImageCodeKey()),"验证码key不能为空");
        Assert.isTrue( redisTemplate.hasKey(loginDto.getImageCodeKey()),"验证码已经过期");
        Assert.isTrue(loginDto.getImageCode().equals(redisTemplate.opsForValue().get(loginDto.getImageCodeKey())),"验证码错误");
        Object o1 = redisTemplate.opsForValue().get(loginDto.getPhone());
        String code = StrUtils.getRandomString(6);
        if(o1!=null){
            Assert.isTrue(redisTemplate.getExpire(loginDto.getPhone(),TimeUnit.SECONDS)<240,"你点太快了");
            log.info((String) o1);
        }else {
            redisTemplate.opsForValue().set(loginDto.getPhone(),code,300,TimeUnit.SECONDS);
            log.info(code);
        }

    }
}
