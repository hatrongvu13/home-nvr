package com.xxx.data.dto.user;

import lombok.Builder;

@Builder
public record UserDto(String username,
                      String email,
                      String fullName,
                      String phone,
                      String roles) {
}
