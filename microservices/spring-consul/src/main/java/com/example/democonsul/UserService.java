package com.example.democonsul;

import com.example.democonsul.entity.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getByUsername(String username);
}