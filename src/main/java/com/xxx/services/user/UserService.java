package com.xxx.services.user;

import com.xxx.data.dto.token.Token;
import com.xxx.data.dto.user.UserDto;
import com.xxx.data.request.user.LoginRequest;
import com.xxx.data.request.user.RegisterRequest;

public interface UserService {
    /**
     * Login Service
     * @param loginRequest
     * @return
     */
    Token login(LoginRequest loginRequest);

    /**
     * Register Service
     * @param registerRequest
     * @return
     */
    Token register(RegisterRequest registerRequest);

    /**
     * Update user Service
     * @param user
     * @return
     */
    Token updateUser(UserDto user);

    /**
     * Refresh token Service
     * @return
     */
    Token refreshToken();

    /**
     * Clear devices Service
     * @return
     */
    Object clearDevices();

    /**
     * Close device Service
     * @param device
     * @return
     */
    Object closeDevice(String device);

    Object info();
}
