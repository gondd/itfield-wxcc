package cn.itfield.wxcc.quertz;

import cn.itfield.wxcc.service.IKillCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KillCoursetoRedis {
    @Autowired
    private IKillCourseService killCourseService;
    @Scheduled(cron = "0/10 * * * * *")
    public void publish(){
        System.out.println("定时任务执行了");
        killCourseService.publish();
    }
}
