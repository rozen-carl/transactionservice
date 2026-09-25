package com.example.transactionservice.dto;

import com.example.transactionservice.entity.Transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferResponse(
        UUID transferId,
        String transferReference,
        UUID sourceAccountId,
        UUID destinationAccountId,
        BigDecimal amount,
        String currency,
        Transfer.TransferType transferType,
        Transfer.TransferStatus status,
        String description,
        Instant createdDate
) {

    public static TransferResponse from(
            Transfer transfer) {

        return new TransferResponse(
                transfer.getId(),
                transfer.getTransferReference(),
                transfer.getSourceAccountId(),
                transfer.getDestinationAccountId(),
                transfer.getAmount(),
                transfer.getCurrency(),
                transfer.getTransferType(),
                transfer.getStatus(),
                transfer.getDescription(),
                transfer.getCreatedDate()
        );
    }
}
