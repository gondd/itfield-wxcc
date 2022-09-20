package cn.itfield.wxcc.filter;

import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.domain.Userinfo;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.utils.jwt.JwtUtils;
import cn.itfield.wxcc.utils.jwt.Payload;
import cn.itfield.wxcc.utils.jwt.RsaUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.List;
@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    private ExcludeProperties excludeProperties;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        RequestPath path = request.getPath();
        String value = path.value();
        System.out.println(value);
        //登录接口放行
        Boolean excluded = excludeProperties.isExcluded(path.value());
        if(excluded) {
            //登录接口放行
            return chain.filter(exchange);
        }
        List<String> token = request.getHeaders().get("U-TOKEN");
            System.out.println(token);
            if (token != null) {
                Login lf = null;
                try {
                    PublicKey publicKey = RsaUtils
                            .getPublicKey(FileCopyUtils
                                    .copyToByteArray(this.getClass().getClassLoader().getResourceAsStream("hrm_auth_rsa.pub")));
                    //解密获取payload对象
                    Payload<Userinfo> payload = JwtUtils.getInfoFromToken(token.get(0), publicKey, Userinfo.class);
                    Userinfo userInfo = payload.getUserInfo();
                    lf = userInfo.getLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (lf != null) {
                    return chain.filter(exchange);
                }

            }
            response.getHeaders().set("content-type", "application/json;charset=UTF-8");
            JsonResult nologin = JsonResult.error("nologin");
            byte[] bytes = JSON.toJSONBytes(nologin);
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(wrap));

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
