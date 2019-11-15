package com.example.democonsul.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
