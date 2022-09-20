package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.CourseCart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
public interface ICourseCartService extends IService<CourseCart> {

    void addshoppingcart(Long loginId, Long courseId);

}
