package com.example.transactionservice.service;

import com.example.transactionservice.client.AccountClient;
import com.example.transactionservice.client.CustomerClient;
import com.example.transactionservice.dto.AccountTransferRequest;
import com.example.transactionservice.dto.CustomerResponse;
import com.example.transactionservice.dto.TransferRequest;
import com.example.transactionservice.entity.Transfer;
import com.example.transactionservice.exception.InvalidTransferAmountException;
import com.example.transactionservice.exception.InvalidTransferException;
import com.example.transactionservice.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl
        implements TransferService {

    private final TransferRepository transferRepository;
    private final CustomerClient customerClient;
    private final AccountClient accountClient;

    @Override
    public Transfer transfer(TransferRequest request) {

        validateRequest(request);

        // 1. Validate customer
        CustomerResponse customer =
                customerClient.getCustomer(
                        request.customerId()
                );

        if (!"ACTIVE".equals(customer.status())) {
            throw new InvalidTransferException(
                    "Customer is not active"
            );
        }

        // 2. Create transfer record
        Transfer transfer =
                Transfer.builder()
                        .transferReference(
                                generateTransferReference()
                        )
                        .customerId(
                                request.customerId()
                        )
                        .sourceAccountId(
                                request.sourceAccountId()
                        )
                        .destinationAccountId(
                                request.destinationAccountId()
                        )
                        .amount(request.amount())
                        .currency(request.currency())
                        .transferType(
                                Transfer.TransferType.OWN_ACCOUNT
                        )
                        .status(
                                Transfer.TransferStatus.PENDING
                        )
                        .description(
                                request.description()
                        )
                        .build();

        transfer = transferRepository.save(transfer);

        // 3. Ask Account Service to perform
        //    the actual money movement
        accountClient.transfer(
                new AccountTransferRequest(
                        transfer.getSourceAccountId(),
                        transfer.getDestinationAccountId(),
                        transfer.getAmount(),
                        transfer.getCurrency(),
                        transfer.getTransferReference()
                )
        );

        // 4. Mark transfer completed
        transfer.setStatus(
                Transfer.TransferStatus.COMPLETED
        );

        return transferRepository.save(transfer);
    }

    private void validateRequest(
            TransferRequest request) {

        if (request == null) {
            throw new InvalidTransferException(
                    "Transfer request must not be null"
            );
        }

        if (request.sourceAccountId() == null) {
            throw new InvalidTransferException(
                    "Source account ID is required"
            );
        }

        if (request.destinationAccountId() == null) {
            throw new InvalidTransferException(
                    "Destination account ID is required"
            );
        }

        if (request.sourceAccountId()
                .equals(request.destinationAccountId())) {

            throw new InvalidTransferException(
                    "Source and destination accounts must be different"
            );
        }

        if (request.amount() == null ||
                request.amount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidTransferAmountException(
                    "Transfer amount must be greater than zero"
            );
        }

        if (request.currency() == null ||
                request.currency().isBlank()) {

            throw new InvalidTransferException(
                    "Currency is required"
            );
        }

        if (request.currency().length() != 3) {
            throw new InvalidTransferException(
                    "Currency must be a 3-letter currency code"
            );
        }
    }

    private String generateTransferReference() {

        return "TRF-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 20)
                        .toUpperCase();
    }
}
