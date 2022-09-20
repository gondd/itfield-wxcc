package cn.itfield.wxcc.lucenter;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.CourseOrder;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.service.ICourseOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@RocketMQTransactionListener(txProducerGroup = ConsTant.TEX_PRODUCER_GROUP)
@Component
public class CourseOrserLucenter implements RocketMQLocalTransactionListener {
    @Autowired
    private ICourseOrderService CourseOrderService;
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            CourseOrder courseOrder = (CourseOrder) o;
            Assert.isTrue(courseOrder!=null,"消息发送失败");
            CourseOrderService.savacourseorder(courseOrder);
            log.info("正在加载事务");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
          e.printStackTrace();
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        try {
            PayOrderDto payload = (PayOrderDto)message.getPayload();
            if(payload.getOrderNo()==null){
                log.info("未发现你的订单");
                return RocketMQLocalTransactionState.ROLLBACK;
            }

            CourseOrder courseorder = CourseOrderService.getOne(new QueryWrapper<CourseOrder>().eq("order_no", payload.getOrderNo()));
            if(courseorder==null){
                log.info("你未购买课程");
                return RocketMQLocalTransactionState.ROLLBACK;

            }
            log.info("正在加载事务");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
