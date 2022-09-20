package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.doc.CourceDoc;
import cn.itfield.wxcc.result.JsonResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Courseclientfallback implements FallbackFactory<CourseClient> {
    @Override
    public CourseClient create(Throwable throwable) {
        return new CourseClient() {
            @Override
            public JsonResult coursesave(List<CourceDoc> courcedocs) {
                throwable.printStackTrace();
                return JsonResult.error("全文解锁有问题"+ throwable.getMessage());
            }

            @Override
            public JsonResult deletebatch(List<CourceDoc> courcedocs) {
                return new JsonResult();
            }
        };
    }
}
