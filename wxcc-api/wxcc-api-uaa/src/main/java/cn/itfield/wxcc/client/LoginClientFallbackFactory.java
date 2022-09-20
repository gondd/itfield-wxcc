package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.result.JsonResult;
import feign.hystrix.FallbackFactory;

public class LoginClientFallbackFactory implements FallbackFactory<LoginClient> {
    @Override
    public LoginClient create(Throwable throwable) {
        return new LoginClient() {
            @Override
            public JsonResult saveOrUpdate(Login login) {
                return JsonResult.error("服务调用错误了");
            }
        };
    }
}
