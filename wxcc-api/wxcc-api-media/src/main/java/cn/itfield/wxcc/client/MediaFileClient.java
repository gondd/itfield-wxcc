package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.MediaFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "service-media",fallbackFactory = MediaFileClientFallback.class)
public interface MediaFileClient {
    @GetMapping("/mediaFile/getvoide/{courseId}")
    List<MediaFile> getvoide(@PathVariable("courseId") Long courseId);
}
