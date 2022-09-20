package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.Course;
import cn.itfield.wxcc.domain.CourseUserLearn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-course",fallbackFactory = CourseClientFallback.class)
public interface CourseClient {
    @GetMapping("/courseUserLearn/getkechen/{courseId}/{loginId}")
    CourseUserLearn getkechen(@PathVariable("courseId") Long courseId, @PathVariable("loginId")Long loginId);
    @GetMapping("/course/getcourse/{courseId}")
    Course get(@PathVariable("courseId")Long courseId);
}
