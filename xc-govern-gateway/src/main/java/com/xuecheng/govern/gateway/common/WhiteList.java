package com.xuecheng.govern.gateway.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "whitelist")
@Data
@Component
public class WhiteList {

    private List<String> include;
}
