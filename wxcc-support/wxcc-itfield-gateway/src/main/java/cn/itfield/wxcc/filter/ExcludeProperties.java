package cn.itfield.wxcc.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 读取yml中的spring.cloud.gateway.exclude数组配置
 *  这些就是要放行的地址
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class ExcludeProperties {

    private List<String> excludes;

    /**
     * 判断本次请求地址是否需要放行
     * @param requestUri
     * @return
     */
    public Boolean isExcluded(String requestUri){
        System.out.println(requestUri);
        for (String exclude : excludes) {
            // /ymcc/system/employee/pagelist
            if(exclude.endsWith("/**")){
                //截取字符串  去掉/**
                String prefix = exclude.substring(0, exclude.indexOf("/**"));
                if(!requestUri.startsWith(prefix)){
                    continue;
                }
                return requestUri.startsWith(prefix);
            }
        }
        return excludes.contains(requestUri);
    }
}