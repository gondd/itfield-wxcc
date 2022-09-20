package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.CourseOrder;
import cn.itfield.wxcc.domain.CourseOrderItem;
import cn.itfield.wxcc.domain.dto.CourseOrderDto;
import cn.itfield.wxcc.domain.dto.MessageStationDto;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.domain.dto.PreCreatedOrderDto;
import cn.itfield.wxcc.mapper.CourseOrderMapper;
import cn.itfield.wxcc.service.ICourseOrderItemService;
import cn.itfield.wxcc.service.ICourseOrderService;
import cn.itfield.wxcc.utils.CodeGenerateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
@Slf4j
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private ICourseOrderItemService courseOrderItemService;
    @Override
    public String courseOrder(CourseOrderDto courseOrderDto) {
        System.out.println(courseOrderDto);
        Assert.isTrue(courseOrderDto.getToken()!=null,"非法请求");
        Assert.isTrue(redisTemplate.hasKey(courseOrderDto.getToken()),"非法请求");
        Assert.isTrue(!courseOrderDto.getCourseOrderItems().isEmpty(),"课程不能为空");
        //幂等性校验
        CourseOrder courseOrder = new CourseOrder();
        String s = CodeGenerateUtils.generateOrderSn(courseOrderDto.getUserId());
        courseOrder.setOrderNo(s);
        courseOrder.setTotalAmount(courseOrderDto.getTotalAmount());
        courseOrder.setPayAmount(courseOrderDto.getTotalAmount());
        courseOrder.setTotalCount(courseOrderDto.getCourseIds().size());
        courseOrder.setUserId(courseOrderDto.getUserId());
        courseOrder.setTitle(courseOrderDto.getType()==0?"普通订单":"秒杀订单");
        List<CourseOrderItem> courseOrderItems = courseOrderDto.getCourseOrderItems();
        courseOrder.setCourseOrderItems(courseOrderItems);
        List<Long> courseIds = courseOrderDto.getCourseIds();
        String join = StringUtils.join(courseIds, ",");
        System.out.println("ids是。。。。。。"+join);
        PayOrderDto payOrderDto = new PayOrderDto(courseOrder.getTotalAmount(),courseOrder.getPayType(),join,courseOrder.getOrderNo(),null,courseOrder.getTitle(),courseOrder.getUserId());
        log.info("正在发送订单消息");
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(ConsTant.TEX_PRODUCER_GROUP, ConsTant.NEWS_NAME_PUSH_GROUP + ":" + ConsTant.NEWS_NAME_HH,
                MessageBuilder.withPayload(payOrderDto).build(), courseOrder);
        //发送普通消息通知用户下单成功
        log.info("发送普通消息通知用户下单成功");
       /* String msg = String.format(ConsTant.MESSAGE_TEMPLATE, courseOrderDto.getUserId(),courseOrderDto.getUserId());*/
        ArrayList<Long> userids = new ArrayList<>();
        userids.add(courseOrderDto.getUserId());
        MessageStationDto messageStationDto = new MessageStationDto("课程下单", "课程下单成功", "系统消息", 1,userids);
        rocketMQTemplate.sendOneWay(ConsTant.NEWS_NAME_PUSH + ":" + ConsTant.NEWS_NAME_state, MessageBuilder.withPayload(JSON.toJSONString(messageStationDto)).build());
        log.info("发送成功");
        redisTemplate.delete(courseOrderDto.getToken());
        return s;

    }

    @Override
    public void savacourseorder(CourseOrder courseOrder) {
        baseMapper.insert(courseOrder);
        List<CourseOrderItem> courseOrderItems = courseOrder.getCourseOrderItems();
        courseOrderItems.forEach(courseOrderItem -> courseOrderItem.setOrderId(courseOrder.getId()));
        courseOrderItemService.saveBatch(courseOrderItems);
    }

    @Override
    public String killorder(CourseOrderDto courseOrderDto) {
        Assert.isTrue(courseOrderDto.getToken()!=null,"非法请求");
        Assert.isTrue(redisTemplate.hasKey(courseOrderDto.getToken()),"非法请求");
        CourseOrder courseOrder = new CourseOrder();
        PreCreatedOrderDto preCreatedOrderDto= (PreCreatedOrderDto) redisTemplate.opsForValue().get(courseOrderDto.getOrderNo());
        BeanUtils.copyProperties(courseOrder,preCreatedOrderDto);
        CourseOrderItem courseOrderItem = new CourseOrderItem();
        BeanUtils.copyProperties(courseOrderItem,preCreatedOrderDto);
        courseOrder.getCourseOrderItems().add(courseOrderItem);
        Long courseId = preCreatedOrderDto.getCourseId();

        PayOrderDto payOrderDto = new PayOrderDto(courseOrder.getTotalAmount(),courseOrder.getPayType(),String.valueOf(courseId),courseOrder.getOrderNo(),null,courseOrder.getTitle(),courseOrder.getUserId());
        log.info("正在发送订单消息");
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(ConsTant.TEX_PRODUCER_GROUP, ConsTant.NEWS_NAME_PUSH_GROUP + ":" + ConsTant.NEWS_NAME_HH,
                MessageBuilder.withPayload(payOrderDto).build(), courseOrder);
        //发送普通消息通知用户下单成功
        log.info("发送普通消息通知用户下单成功");
        /* String msg = String.format(ConsTant.MESSAGE_TEMPLATE, courseOrderDto.getUserId(),courseOrderDto.getUserId());*/
        ArrayList<Long> userids = new ArrayList<>();
        userids.add(courseOrderDto.getUserId());
        MessageStationDto messageStationDto = new MessageStationDto("课程下单", "课程下单成功", "系统消息", 1,userids);
        rocketMQTemplate.sendOneWay(ConsTant.NEWS_NAME_PUSH + ":" + ConsTant.NEWS_NAME_state, MessageBuilder.withPayload(JSON.toJSONString(messageStationDto)).build());
        log.info("发送成功");
        redisTemplate.delete(courseOrderDto.getToken());
        return courseOrderDto.getOrderNo();
    }




}
