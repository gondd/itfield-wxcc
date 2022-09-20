package cn.itfield.wxcc.message;

import cn.itfield.wxcc.domain.CourseOrder;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.mapper.CourseOrderMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic ="dingdan"
        ,consumerGroup = "public-message-dingxiaofei1",messageModel = MessageModel.CLUSTERING,
        selectorExpression = "zhuangtai"
)
@Slf4j
public class PayOrderMessageCHU implements RocketMQListener<MessageExt>{
    @Autowired
    private CourseOrderMapper courseOrderMapper;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            String s = new String(body, "utf-8");
            PayOrderDto payOrderDto = JSON.parseObject(s, PayOrderDto.class);
            CourseOrder courseOrder = courseOrderMapper.selectOne(new QueryWrapper<CourseOrder>().eq("order_no", payOrderDto.getOrderNo()));
            courseOrder.setStatusOrder(payOrderDto.getState());
            courseOrderMapper.updateById(courseOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
