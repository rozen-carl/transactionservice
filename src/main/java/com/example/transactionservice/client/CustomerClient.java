package com.example.transactionservice.client;

import com.example.transactionservice.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestClient customerRestClient;

    public CustomerResponse getCustomer(
            UUID customerId) {

        return customerRestClient
                .get()
                .uri("/api/v1/customers/{customerId}", customerId)
                .retrieve()
                .body(CustomerResponse.class);
    }
}
