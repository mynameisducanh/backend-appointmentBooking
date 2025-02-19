package com.example.backend.services;

import com.example.backend.dtos.LoginResponseDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    LoginResponseDTO login(String phoneNumber, String password) throws Exception;
}

