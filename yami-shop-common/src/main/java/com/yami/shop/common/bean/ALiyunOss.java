package com.yami.shop.common.bean;

import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * 用于Ali产品的配置 （OSS）
 * @author: Orcas
 * @version:1.0.0
 * @date: 2021/4/2
 */
// @PropertySource("classpath:xxxx.properties") 默认路径下可不加
@Data
public class ALiyunOss {
    // 地域节点
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    private String bucketName;
    private String urlPrefix;
    private OSSClient ossClient;

//    @Bean
//    public OSSClient ossClient() {
//        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
//    }
}
