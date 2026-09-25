package com.example.transactionservice.client;

import com.example.transactionservice.dto.AccountTransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final RestClient accountRestClient;

    public void transfer(
            AccountTransferRequest request) {

        accountRestClient
                .post()
                .uri("/api/v1/internal/accounts/transfer")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
