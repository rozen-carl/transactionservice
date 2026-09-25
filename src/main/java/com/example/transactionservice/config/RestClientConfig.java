package com.example.transactionservice.config;

import com.example.transactionservice.security.BearerTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final BearerTokenInterceptor bearerTokenInterceptor;

    @Bean
    public RestClient accountRestClient(
            @Value("${services.account.url}")
            String accountServiceUrl) {

        return RestClient.builder()
                .baseUrl(accountServiceUrl)
                .requestInterceptor(bearerTokenInterceptor)
                .build();
    }

    @Bean
    public RestClient customerRestClient(
            @Value("${services.customer.url}")
            String customerServiceUrl) {

        return RestClient.builder()
                .baseUrl(customerServiceUrl)
                .requestInterceptor(bearerTokenInterceptor)
                .build();
    }
}
