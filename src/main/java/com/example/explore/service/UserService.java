package com.example.explore.service;

import com.example.explore.model.service.UserServiceModel;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findById(Long id);

    UserServiceModel findByEmail(String email);

    UserServiceModel findByUsername(String username);

}
