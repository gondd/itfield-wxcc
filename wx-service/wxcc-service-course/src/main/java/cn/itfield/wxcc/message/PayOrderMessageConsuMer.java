package cn.itfield.wxcc.message;

import cn.itfield.wxcc.domain.CourseSummary;
import cn.itfield.wxcc.domain.CourseUserLearn;
import cn.itfield.wxcc.domain.dto.PayOrderDto;
import cn.itfield.wxcc.mapper.CourseSummaryMapper;
import cn.itfield.wxcc.mapper.CourseUserLearnMapper;
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
@RocketMQMessageListener(topic ="topig_name_shh"
        ,consumerGroup = "public-message-xiaofei",messageModel = MessageModel.CLUSTERING,
        selectorExpression = "pigic_namehh"
)
@Slf4j
public class PayOrderMessageConsuMer implements RocketMQListener<MessageExt>{
    @Autowired
    private CourseSummaryMapper courseSummaryMapper;
    @Autowired
    private CourseUserLearnMapper courseUserLearnMapper;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            byte[] body = messageExt.getBody();
            String s = new String(body, "utf-8");
            PayOrderDto payOrderDto = JSON.parseObject(s, PayOrderDto.class);
            String id = payOrderDto.getRelationId();
            String[] split = id.split(",");
            CourseUserLearn courseUserLearn = new CourseUserLearn();
            for (String s1 : split) {
                courseUserLearn.setCourseId(Long.valueOf(s1));
                courseUserLearn.setCourseOrderNo(payOrderDto.getOrderNo());
                courseUserLearn.setLoginId(payOrderDto.getUserId());
                courseUserLearn.setState(0);
                courseUserLearnMapper.insert(courseUserLearn);
                CourseSummary courseSummary = courseSummaryMapper.selectOne(new QueryWrapper<CourseSummary>().eq("course_id", s1));
                courseSummary.setSaleCount(courseSummary.getSaleCount()+1);
                courseSummaryMapper.updateById(courseSummary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
