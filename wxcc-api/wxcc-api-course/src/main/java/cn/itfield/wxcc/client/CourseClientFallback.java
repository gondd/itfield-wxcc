package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.CourseUserLearn;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CourseClientFallback implements FallbackFactory<CourseClient> {
    @Override
    public CourseClient create(Throwable throwable) {
        return new CourseClient() {
            @Override
            public CourseUserLearn getkechen(Long courseId, Long loginId) {
                return new CourseUserLearn();
            }

            @Override
            public Course get(Long courseId) {
                return new Course();
            }
        };
    }
}
