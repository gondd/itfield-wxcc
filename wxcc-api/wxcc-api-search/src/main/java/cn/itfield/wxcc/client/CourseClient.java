package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.doc.CourceDoc;
import cn.itfield.wxcc.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(value = "service-search",fallbackFactory = Courseclientfallback.class)
public interface CourseClient {
    @PostMapping("/search/saveBatch")
    JsonResult coursesave(@RequestBody List<CourceDoc> courcedocs);
    @PostMapping("/search/deletebatch")
    JsonResult deletebatch(@RequestBody List<CourceDoc> courcedocs);
}
