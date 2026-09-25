package com.example.transactionservice.service;

import com.example.transactionservice.dto.TransferRequest;
import com.example.transactionservice.entity.Transfer;

public interface TransferService {

    Transfer transfer(TransferRequest request);
}
