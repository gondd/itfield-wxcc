package cn.itfield.wxcc.consumer;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.MessageSms;
import cn.itfield.wxcc.domain.dto.MessageSmsDto;
import cn.itfield.wxcc.mapper.MessageSmsMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
@Component
@RocketMQMessageListener(topic = ConsTant.NEWS_NAME_PUSH
        ,consumerGroup = "message-consumer-sms",messageModel = MessageModel.CLUSTERING,
        selectorExpression = ConsTant.NEWS_NAME_SMS
)
@Slf4j
public class SmsConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private MessageSmsMapper mapper;
    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println(1111);
        try {
            byte[] body = messageExt.getBody();
            Assert.isTrue(body!=null,"消息为空");
            String s = new String(body, "utf-8");
            MessageSmsDto messageSmsDto = JSON.parseObject(s, MessageSmsDto.class);
            System.out.println(messageSmsDto);
            //发送消息
            log.info("发送短信中{}");
            messageSmsDto.getMessageSms().forEach(messageSms -> {
                MessageSms messageSms1 = new MessageSms();
                BeanUtils.copyProperties(messageSmsDto,messageSms1);
                messageSms1.setUserId(messageSms.getUserId());
                messageSms1.setIp(messageSms.getIp());
                messageSms1.setPhone(messageSms.getPhone());
                mapper.insert(messageSms1);

            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
