package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(value = "service-uaa",fallbackFactory = LoginClientFallbackFactory.class)
public interface LoginClient {
    @PostMapping("/login/save")
    JsonResult saveOrUpdate(@RequestBody Login login);
}
