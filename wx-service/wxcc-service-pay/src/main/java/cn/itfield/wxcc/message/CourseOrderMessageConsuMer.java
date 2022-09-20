package cn.itfield.wxcc.message;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.PayOrder;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.service.IPayOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = ConsTant.NEWS_NAME_PUSH_GROUP
        ,consumerGroup = "message-consumer-order",messageModel = MessageModel.CLUSTERING,
        selectorExpression = ConsTant.NEWS_NAME_HH
)
@Slf4j
public class CourseOrderMessageConsuMer implements RocketMQListener<MessageExt>{
    @Autowired
    private IPayOrderService payOrderService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            String s = new String(body, "utf-8");
            PayOrderDto payOrderDto = JSON.parseObject(s, PayOrderDto.class);
            PayOrder payOrder = new PayOrder();
            BeanUtils.copyProperties(payOrderDto,payOrder);
            payOrder.setRelationId(payOrderDto.getRelationId());
            log.info("正在保存");
            payOrderService.save(payOrder);
            payOrder.setState(4);
            rocketMQTemplate.syncSend(ConsTant.TIG_NAME_PUSH+":"+ConsTant.TGS_ZHIFU_TWO,
                    MessageBuilder.withPayload(JSON.toJSONString(payOrder)).build(),3000,16);
            payOrder.setState(5);
            System.out.println("发送没");
            rocketMQTemplate.syncSend(ConsTant.TIG_NAME_PUSH+":"+ConsTant.TGS_ZHIFU_TWO,
                    MessageBuilder.withPayload(JSON.toJSONString(payOrder)).build(),3000,18);
            System.out.println("发送没");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
