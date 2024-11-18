package com.beaconstrategists.oauth2client.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientConfig {

    @Value("${CLIENT_CONN_TIMEOUT:2000}")
    public Long clientConnectionTimeout;

    @Value("${CLIENT_RESP_TIMEOUT:3000}")
    public Long clientResponseTimeout;

    @Value("${RESOURCE_BASE_URI:http://localhost:8080/api/v1}")
    public String resourceBaseUrl;

    @Bean
    public RestClient restClient() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(clientConnectionTimeout, TimeUnit.MILLISECONDS)
                .setResponseTimeout(clientResponseTimeout, TimeUnit.MILLISECONDS)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return RestClient.builder()
                .baseUrl(resourceBaseUrl)
                .defaultHeader("Accept", "application/json")
                .requestFactory(requestFactory)
                .build();

//        return RestClient.builder()
//                .baseUrl("http://localhost:8080/api/v1")
//                .defaultHeader("Accept", "application/json")
//                .build();
    }

}
