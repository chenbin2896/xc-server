package com.xuecheng.search.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticSearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String esUrl;

    @Bean
    public RestHighLevelClient restHighLevelClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.create(esUrl);
        return RestClients.create(clientConfiguration).rest();
    }
}
