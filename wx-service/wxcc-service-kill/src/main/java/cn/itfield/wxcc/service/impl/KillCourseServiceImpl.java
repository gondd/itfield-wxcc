package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.constant.ConsTant;
import cn.itfield.wxcc.domain.KillActivity;
import cn.itfield.wxcc.domain.KillCourse;
import cn.itfield.wxcc.domain.KillOrderDto;
import cn.itfield.wxcc.domain.dto.PreCreatedOrderDto;
import cn.itfield.wxcc.mapper.KillActivityMapper;
import cn.itfield.wxcc.mapper.KillCourseMapper;
import cn.itfield.wxcc.service.IKillCourseService;
import cn.itfield.wxcc.utils.CodeGenerateUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-26
 */
@Service
public class KillCourseServiceImpl extends ServiceImpl<KillCourseMapper, KillCourse> implements IKillCourseService {
   @Autowired
   private KillActivityMapper killActivityMapper;
   @Autowired
   private KillCourseMapper killCourseMapper;
   @Autowired
   private RedissonClient redissonClient;
   @Autowired
   private RocketMQTemplate rocketMQTemplate;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Transactional
    @Override
    public boolean save(KillCourse entity) {
        Assert.isTrue(entity.getCourseId()!=null,"未选中课程");
        Assert.isTrue(entity.getCourseName()!=null,"课程名字不能为空");
        Assert.isTrue(entity.getActivityId()!=null,"活动ID不能为空");
        KillActivity killActivity = killActivityMapper.selectById(entity.getActivityId());
        entity.setStartTime(killActivity.getBeginTime());
        entity.setTimeStr(killActivity.getTimeStr());
        entity.setEndTime(killActivity.getEndTime());
        Integer integer = killCourseMapper.selectCount(null);
        entity.setKillSort(integer);
        return super.save(entity);
    }

    @Override
    public void publish() {
        Date date = new Date();
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(ConsTant.KEY_KILL_COURSE_STORE);
        List<KillCourse> killCourses = baseMapper.selectList(new QueryWrapper<KillCourse>().le("start_time", date).ge("end_time", date).eq("publish_status",0).orderByAsc("kill_sort"));
        Map<String, Object> map = new HashMap<>();
        //预热库存到redis
        killCourses.forEach(killCourse -> {
            redisTemplate.opsForValue().set(ConsTant.KILL_COURSE_STORE + killCourse.getId()
                    , killCourse.getKillCount());
            killCourse.setPublishStatus(1);
            killCourseMapper.updateById(killCourse);
            //预热课程到redis
            map.put(killCourse.getId().toString(),killCourse);
            killCourse.setKillstates(UUID.randomUUID().toString());
        });
        hashOps.putAll(map);

    }

    @Override
    public List listtoRedis() {
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(ConsTant.KEY_KILL_COURSE_STORE);
        return hashOps.values();
    }

    @Override
    public KillCourse killcoursebyid(Long id) {
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(ConsTant.KEY_KILL_COURSE_STORE);;
        return (KillCourse) hashOps.get(String.valueOf(id));
    }

    @Override
    public String kill(KillOrderDto killOrderDto) {
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(ConsTant.KEY_KILL_COURSE_STORE);;
        KillCourse killCourse = (KillCourse) hashOps.get(String.valueOf(killOrderDto.getKillcourseid()));
        Assert.isTrue(!killOrderDto.getKillstates().equals(killCourse.getKillstates()),"验证码错误！");
        Date date = new Date();
        Assert.isTrue(killCourse.getStartTime().before(date)&&killCourse.getEndTime().after(date),"不在秒杀时间以内");
        /*Assert.isTrue(redisTemplate.hasKey(killOrderDto.getUserid()+"-"+ killOrderDto.getKillcourseid()),"已经秒杀当前课程了");*/
        RSemaphore semaphore = redissonClient.getSemaphore(ConsTant.KILL_COURSE_STORE + killOrderDto.getKillcourseid());
        boolean b = semaphore.tryAcquire(killCourse.getKillCount());
        Assert.isTrue(b,"获取锁失败");
        PreCreatedOrderDto preCreatedOrderDto = new PreCreatedOrderDto();
        String orderNo = CodeGenerateUtils.generateOrderSn(killOrderDto.getUserid());
        preCreatedOrderDto.setOrderNo(orderNo);
        preCreatedOrderDto.setCourseId(killCourse.getCourseId());
        preCreatedOrderDto.setKillCourseId(killCourse.getId());
        preCreatedOrderDto.setCoursePic(killCourse.getCoursePic());
        preCreatedOrderDto.setCourseName(killCourse.getCourseName());
        preCreatedOrderDto.setOrderType(2);
        preCreatedOrderDto.setAmount(killCourse.getKillPrice());
        preCreatedOrderDto.setCount(killCourse.getKillLimit());
        preCreatedOrderDto.setDiscountAmount(killCourse.getKillPrice());
        preCreatedOrderDto.setPayAmount(killCourse.getKillPrice());
        preCreatedOrderDto.setUserId(killOrderDto.getUserid());
        preCreatedOrderDto.setDiscountAmount(killCourse.getKillPrice());
        preCreatedOrderDto.setTitle("秒杀下单");
        preCreatedOrderDto.setTotalAmount(killCourse.getKillPrice());
        preCreatedOrderDto.setTotalCount(killCourse.getKillLimit());
        redisTemplate.opsForValue().set(orderNo,preCreatedOrderDto);

        redisTemplate.opsForValue().set(killOrderDto.getUserid().toString()+"-"+ killOrderDto.getKillcourseid(),"1111");
        rocketMQTemplate.syncSend(ConsTant.KILL_COURSE_PUSH+":"+ConsTant.V_KILL_COURSE_PUSH, MessageBuilder.withPayload(JSON.toJSONString(preCreatedOrderDto)).build(),3000,2);

        return orderNo;
    }
}
