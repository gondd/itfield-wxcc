package cn.itfield.wxcc.web.controller;

import cn.itfield.wxcc.domain.MediaFile;
import cn.itfield.wxcc.exception.GlobalException;
import cn.itfield.wxcc.service.IMediaFileService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RocketMQMessageListener(topic = "media-po-push",
        selectorExpression="media-jo-push"        ,consumerGroup = "service-consumer"
        ,messageModel = MessageModel.CLUSTERING
)
public class MessageConsumer implements RocketMQListener<MessageExt> {
    @Autowired
    private IMediaFileService mediaFileService;


    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        if(body == null || body.length == 0){
            throw new GlobalException("消息内容是空的！");
        }
        try {
            String jsonStr = new String(body, "utf-8");
            MediaFile mediaFile = JSON.parseObject(jsonStr, MediaFile.class);
            mediaFileService.handleFile2m3u8(mediaFile);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}
