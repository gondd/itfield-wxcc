package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.domain.MediaFile;
import cn.itfield.wxcc.service.IMediaProducerl;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MediaProducerlImpl implements IMediaProducerl {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public Boolean syncSend(MediaFile mediaFile) {
        SendResult sendResult = rocketMQTemplate.syncSend("media-po-push:media-jo-push", MessageBuilder.withPayload(JSON.toJSONString(mediaFile)).build());
        SendStatus sendStatus = sendResult.getSendStatus();
        if (sendResult.equals("SEND_OK")){
            return true;
        }
        return false;
    }
}
