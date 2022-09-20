package cn.itfield.wxcc.consumer;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.MessageEmail;
import cn.itfield.wxcc.domain.dto.MessageEmailDto;
import cn.itfield.wxcc.mapper.MessageEmailMapper;
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
        ,consumerGroup = "message-consumer-email",messageModel = MessageModel.CLUSTERING,
        selectorExpression = ConsTant.NEWS_NAME_email
)
@Slf4j
public class EmailConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private MessageEmailMapper messageEmailMapper;

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            Assert.isTrue(body!=null,"消息为空");
            String s = new String(body, "utf-8");
            MessageEmailDto messageEmailDto = JSON.parseObject(s, MessageEmailDto.class);
            System.out.println(messageEmailDto);
            log.info("正在发送邮件");

            messageEmailDto.getMessageEmails().forEach(messageEmails -> {
                MessageEmail messageEmail = new MessageEmail();
                BeanUtils.copyProperties(messageEmailDto,messageEmail);
                messageEmail.setUserId(messageEmails.getUserId());
                messageEmail.setCopyto(messageEmails.getCopyto());
                messageEmail.setEmail(messageEmails.getEmail());
                messageEmailMapper.insert(messageEmail);
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
