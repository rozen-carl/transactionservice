package com.example.transactionservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        UUID customerId,
        UUID sourceAccountId,
        UUID destinationAccountId,
        BigDecimal amount,
        String currency,
        String description
) {
}
