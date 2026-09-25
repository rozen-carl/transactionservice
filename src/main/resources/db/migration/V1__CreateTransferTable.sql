CREATE TABLE transfer
(
    id                     BINARY(16) NOT NULL,
    transfer_reference     VARCHAR(30)    NOT NULL,

    customer_id            BINARY(16) NOT NULL,

    source_account_id      BINARY(16) NOT NULL,
    destination_account_id BINARY(16) NOT NULL,

    amount                 DECIMAL(19, 2) NOT NULL,
    currency               VARCHAR(3)     NOT NULL,

    transfer_type          VARCHAR(30)    NOT NULL,
    status                 VARCHAR(20)    NOT NULL,

    description            VARCHAR(255),

    created_date           TIMESTAMP      NOT NULL,
    updated_date           TIMESTAMP      NOT NULL,

    PRIMARY KEY (id),

    UNIQUE KEY uk_transfer_reference (
        transfer_reference
        ),

    INDEX                  idx_transfers_customer (
        customer_id
    ),

    INDEX                  idx_transfers_source_account (
        source_account_id
    ),

    INDEX                  idx_transfers_destination_account (
        destination_account_id
    ),

    INDEX                  idx_transfers_status (
        status
    ),

    CONSTRAINT chk_transfer_amount
        CHECK (amount > 0)
);