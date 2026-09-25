package com.example.transactionservice.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String status
) {
}
