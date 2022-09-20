package cn.itfield.wxcc.message;

import cn.itfield.wxcc.config.AlipayProp;
import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.PayOrder;
import cn.itfield.wxcc.domain.dto.MessageStationDto;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.service.IPayOrderService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
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
@RocketMQMessageListener(topic = ConsTant.TIG_NAME_PUSH
        ,consumerGroup = "message-consumer-pay1",messageModel = MessageModel.CLUSTERING,
        selectorExpression = ConsTant.TGS_ZHIFU_TWO
)
@Slf4j
public class CancelPaymentMessage implements RocketMQListener<MessageExt> {
    @Autowired
    private IPayOrderService payOrderService;
    @Autowired
    private AlipayProp alipayProp;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            String s = new String(body, "utf-8");
            PayOrder payOrder = JSON.parseObject(s,PayOrder.class);
            PayOrder byId = payOrderService.getById(payOrder.getId());
            if(byId.getPayStatus()!=1&&byId.getPayStatus()!=3&&byId.getPayStatus()!=5) {
                log.info("正在修改");
                payOrder.setPayStatus(payOrder.getState());
                payOrderService.updateById(payOrder);
                System.out.println("发送没");

                    AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", alipayProp.getAppId(), alipayProp.getMerchantPrivateKey(), "json", "utf-8", alipayProp.getAlipayPublicKey(), "RSA2");
                    AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
                    JSONObject bizContent = new JSONObject();
                    bizContent.put("out_trade_no", payOrder.getOrderNo());
                    request.setBizContent(bizContent.toString());
                    AlipayTradeCancelResponse response = alipayClient.execute(request);
                    if (response.isSuccess()) {
                        System.out.println("调用成功");
                    } else {
                        System.out.println("调用失败");
                    }
                PayOrderDto payOrderDto = new PayOrderDto();
                MessageStationDto messageStationDto =null;
                BeanUtils.copyProperties(payOrder,payOrderDto);
                rocketMQTemplate.sendOneWay("dingdan:zhuangtai",MessageBuilder.withPayload(JSON.toJSONString(payOrderDto)).build());
                if (payOrder.getState() == 4) {
                    System.out.println("发送没33");
                    messageStationDto =new MessageStationDto("课程下单", "课程下单失败", "系统消息", 1, payOrder.getUserId());

                } else {
                    messageStationDto =new MessageStationDto("课程确认单", "课程确认成功", "系统消息", 1, payOrder.getUserId());
                }
                rocketMQTemplate.sendOneWay(ConsTant.NEWS_NAME_PUSH + ":" + ConsTant.NEWS_NAME_state, MessageBuilder.withPayload(JSON.toJSONString(messageStationDto)).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
