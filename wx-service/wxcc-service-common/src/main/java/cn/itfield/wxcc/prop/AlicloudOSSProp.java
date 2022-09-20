package cn.itfield.wxcc.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file.alicloud")
public class AlicloudOSSProp {

    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String endpoint;
}