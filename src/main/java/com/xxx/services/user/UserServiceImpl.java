package com.xxx.services.user;

import com.xxx.data.dto.token.Token;
import com.xxx.data.dto.user.UserAuthentication;
import com.xxx.data.dto.user.UserDto;
import com.xxx.data.entity.UserEntity;
import com.xxx.data.request.user.LoginRequest;
import com.xxx.data.request.user.RegisterRequest;
import com.xxx.exception.DomainCode;
import com.xxx.exception.XxxException;
import com.xxx.repository.UserRepository;
import com.xxx.utils.security.SecurityUtils;
import com.xxx.utils.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public Token login(LoginRequest loginRequest) {
        log.info("START login with : {}", loginRequest);
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    throw new XxxException(DomainCode.ACCOUNT_NOT_FOUND);
                });

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new XxxException(DomainCode.ACCOUNT_WRONG);
        }
        return buildToken(user);
    }

    @Override
    public Token register(RegisterRequest registerRequest) {
        log.info("START register : {}", registerRequest);
        if (userRepository.existsByEmail(registerRequest.email())
                || userRepository.existsByUsername(registerRequest.username())) {
            throw new XxxException(DomainCode.ACCOUNT_ALREADY_EXIST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        user.setFullName(registerRequest.fullName());
        user.setPhone(registerRequest.phone());

        userRepository.save(user);

        return buildToken(user);
    }

    @Override
    public Token updateUser(UserDto user) {
        return null;
    }

    @Override
    public Token refreshToken() {
        String userCurrent = SecurityUtils.getCurrentUserLogin().orElse(null);
        log.info("START refresh token : {}", userCurrent);
        if (StringUtils.isNotBlank(userCurrent)) {
            UserEntity user = userRepository.findByUsername(userCurrent).orElse(null);
            return buildToken(user);
        }
        return null;
    }

    @Override
    public Object clearDevices() {
        return null;
    }

    @Override
    public Object closeDevice(String device) {
        return null;
    }

    @Override
    public Object info() {
        String currentUser = SecurityUtils.getCurrentUserLogin().orElse(null);
        log.info("START info : {}", currentUser);

        UserEntity user = userRepository.findByUsername(currentUser).orElseThrow();
        return UserDto.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    /**
     * build token
     * @param user
     * @return
     */
    private Token buildToken(UserEntity user) {
        log.info("START build token : {}", user);
        return new Token(tokenProvider.createToken(
                new UserAuthentication(user.getId().toString(),
                        user.getUsername(),
                        user.getEmail(),
                        Collections.singleton(new SimpleGrantedAuthority("USER")
                        )), true));
    }
}
