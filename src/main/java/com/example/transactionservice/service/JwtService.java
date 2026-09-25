package com.example.transactionservice.service;

public interface JwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token);
}
