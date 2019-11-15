package com.example.democonsul.jwt.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}
