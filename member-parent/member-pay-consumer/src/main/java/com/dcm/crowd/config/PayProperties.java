package com.dcm.crowd.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "ali.pay")

public class PayProperties {


    private  String gatewayUrl;
    private  String appId;
    private  String merchantPrivateKey;
    private  String charset;
    private  String alipayPublicKey;
    private  String signType;

    private String returnUrl;

   private String notifyUrl;
}
