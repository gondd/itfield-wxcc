package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.client.CourseClient;
import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.CourseCart;
import cn.itfield.wxcc.mapper.CourseCartMapper;
import cn.itfield.wxcc.service.ICourseCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-19
 */
@Service
public class CourseCartServiceImpl extends ServiceImpl<CourseCartMapper, CourseCart> implements ICourseCartService {
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private CourseCartMapper courseCartMapper;
    @Override
    public void addshoppingcart(Long loginId, Long courseId) {
        Assert.isTrue(loginId!=null&&courseId!=null,"数据为空");
        Course course = courseClient.get(courseId);
        CourseCart courseCart = new CourseCart();
        courseCart.setCourseId(courseId);
        courseCart.setCourseName(course.getName());
        courseCart.setCoursePic(course.getPic());
        courseCart.setCoursePic(course.getPic());
        courseCart.setLoginId(loginId);
        courseCart.setStatus(0);
        courseCart.setGradeName(course.getGradeName());
        courseCartMapper.insert(courseCart);
    }
}
