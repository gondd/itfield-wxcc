/*
package cn.itfield.wxcc.filter;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    //配置Filter作用于那个访问规则上
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes().route(r -> r.path("/**")
                //去掉2个前缀
                        .filters(f -> f.stripPrefix(2)
                        .filter(new My)
                        .addResponseHeader("X-Response-test", "test"))
                        .uri("lb://service-system")
                        .order(0)
                        .id("application-system").id("application-uaa")
                ).build();
    }
    }
*/
