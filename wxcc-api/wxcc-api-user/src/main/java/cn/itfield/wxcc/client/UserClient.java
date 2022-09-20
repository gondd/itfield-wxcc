package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-user",fallbackFactory =UserClientFallback.class )
public interface UserClient {
    @PostMapping("/user/getuser")
    List<User> getuser(@RequestBody List<Long> userids);
}
