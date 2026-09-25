package com.example.transactionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            nullable = false,
            unique = true,
            length = 30
    )
    private String transferReference;

    @Column
    private UUID customerId;

    @Column
    private UUID sourceAccountId;

    @Column
    private UUID destinationAccountId;

    @Column
    private BigDecimal amount;

    @Column
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column
    private TransferType transferType;

    @Enumerated(EnumType.STRING)
    @Column
    private TransferStatus status;

    private String description;

    public enum TransferType {
        OWN_ACCOUNT
    }

    public enum TransferStatus {
        PENDING,
        COMPLETED,
        FAILED
    }
}
