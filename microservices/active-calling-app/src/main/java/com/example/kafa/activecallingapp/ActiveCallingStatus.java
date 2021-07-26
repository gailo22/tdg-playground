package com.example.kafa.activecallingapp;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActiveCallingStatus {
    private String ssoid;
    private boolean status;
}
