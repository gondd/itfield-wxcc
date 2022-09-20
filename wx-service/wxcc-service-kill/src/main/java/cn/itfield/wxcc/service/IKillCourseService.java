package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.KillCourse;
import cn.itfield.wxcc.domain.KillOrderDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-26
 */
public interface IKillCourseService extends IService<KillCourse> {

    void publish();

    List listtoRedis();

    KillCourse killcoursebyid(Long id);

    String kill(KillOrderDto killOrderDto);
}
