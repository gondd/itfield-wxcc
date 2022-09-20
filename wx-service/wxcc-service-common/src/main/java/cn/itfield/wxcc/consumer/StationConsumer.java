package cn.itfield.wxcc.consumer;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.MessageStation;
import cn.itfield.wxcc.domain.dto.MessageStationDto;
import cn.itfield.wxcc.mapper.MessageStationMapper;
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
@RocketMQMessageListener(
        topic = ConsTant.NEWS_NAME_PUSH,
        selectorExpression = ConsTant.NEWS_NAME_state,
        consumerGroup = "message-consumer-station",
        messageModel = MessageModel.CLUSTERING
)
@Slf4j
public class StationConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private MessageStationMapper messageStationMapper;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            Assert.isTrue(body!=null,"消息为空");
            String s = new String(body, "utf-8");
            MessageStationDto messageStationDto = JSON.parseObject(s, MessageStationDto.class);
            System.out.println(messageStationDto);
            log.info("正在保存");
            messageStationDto.getUserids().stream().forEach(userId->{
                MessageStation messageStation = new MessageStation();
                BeanUtils.copyProperties(messageStationDto,messageStation);
                messageStation.setUserId(userId);
                messageStationMapper.insert(messageStation);
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
