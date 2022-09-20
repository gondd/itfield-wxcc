package cn.itfield.wxcc.client;

import cn.itfield.wxcc.domain.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public List<User> getuser(List<Long> userids) {
                return new ArrayList<>();
            }
        };
    }
}
