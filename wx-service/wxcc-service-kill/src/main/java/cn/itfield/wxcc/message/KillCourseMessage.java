package cn.itfield.wxcc.message;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.dto.PreCreatedOrderDto;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

@Component
@RocketMQMessageListener(topic = ConsTant.KILL_COURSE_PUSH
        ,consumerGroup = "public-message-feifei",messageModel = MessageModel.CLUSTERING,
        selectorExpression = ConsTant.V_KILL_COURSE_PUSH
)
@Slf4j
public class KillCourseMessage implements RocketMQListener<MessageExt> {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            String s = new String(body, "utf-8");
            Assert.isTrue(StringUtils.hasText(s),"消息为空");
            PreCreatedOrderDto preCreatedOrderDto = JSON.parseObject(s, PreCreatedOrderDto.class);
            redisTemplate.delete(preCreatedOrderDto.getOrderNo());
            redisTemplate.delete(preCreatedOrderDto.getUserId().toString()+"-"+ preCreatedOrderDto.getKillCourseId());
            RSemaphore semaphore = redissonClient.getSemaphore(ConsTant.KILL_COURSE_STORE + preCreatedOrderDto.getKillCourseId());
            semaphore.addPermits(preCreatedOrderDto.getCount());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
