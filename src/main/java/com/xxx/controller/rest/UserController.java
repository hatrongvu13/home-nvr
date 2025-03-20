package com.xxx.controller.rest;

import com.xxx.data.dto.user.UserDto;
import com.xxx.data.request.user.LoginRequest;
import com.xxx.data.request.user.RegisterRequest;
import com.xxx.data.response.ApiResponse;
import com.xxx.factory.ApiFactory;
import com.xxx.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ApiFactory apiFactory;

    /**
     * Login controller
     * @param loginRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        return apiFactory.success(userService.login(loginRequest));
    }

    /**
     * Register controller
     * @param registerRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        return apiFactory.success(userService.register(registerRequest));
    }

    /**
     * Update Controller
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDto user) {
        return apiFactory.success(userService.updateUser(user));
    }

    /**
     * RefreshToken controller
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken() {
        return apiFactory.success(userService.refreshToken());
    }

    /**
     * Clear all devices controller
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/clear-devices")
    public ResponseEntity<ApiResponse> clearDevices() {
        return apiFactory.success(userService.clearDevices());
    }

    /**
     * Close device controller
     * @param device
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/close/{device}")
    public ResponseEntity<ApiResponse> closeDevice(@PathVariable("device") String device) {
        return apiFactory.success(userService.closeDevice(device));
    }

    /**
     * Info controller
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ResponseEntity<ApiResponse> info() {
        return apiFactory.success(userService.info());
    }
}
