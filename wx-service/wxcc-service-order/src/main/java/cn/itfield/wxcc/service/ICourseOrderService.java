package cn.itfield.wxcc.service;

import cn.itfield.wxcc.domain.CourseOrder;
import cn.itfield.wxcc.domain.dto.CourseOrderDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    String courseOrder(CourseOrderDto courseOrderDto);

    void savacourseorder(CourseOrder courseOrder);

    String killorder(CourseOrderDto courseOrderDto);
}
