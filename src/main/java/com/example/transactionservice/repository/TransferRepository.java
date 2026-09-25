package com.example.transactionservice.repository;
import com.example.transactionservice.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransferRepository
        extends JpaRepository<Transfer, UUID> {

    List<Transfer> findByCustomerIdOrderByCreatedDateDesc(
            UUID customerId
    );
}
