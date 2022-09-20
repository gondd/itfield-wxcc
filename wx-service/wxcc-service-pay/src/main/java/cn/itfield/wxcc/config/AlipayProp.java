package cn.itfield.wxcc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayProp {
    //协议
    private String protocol;
    //网关
    private String gatewayHost;
    //类型
    private String signType;
    //应用id
    private String appId;
    //应用公钥
    private String merchantPrivateKey;
    //应用私钥
    private String alipayPublicKey;
    //异步跳转地址
    private String notifyUrl;
    private String encryptKey;
    private String returnUrl;
}
