package com.xxx.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DomainCode {
    SUCCESS("VX-000", "Success", HttpStatus.OK.value()),
    ERROR("VX-002", "Error", HttpStatus.BAD_REQUEST.value()),
    NOT_PERMISSION("VX-003", "Not permission ! Please contact admin : HA TRONG VU - 0943561685", HttpStatus.FORBIDDEN.value()),
    NOT_FOUND("VX-004", "Not found !", HttpStatus.NOT_FOUND.value()),
    INTERNAL_SERVER("VX-005", "Internal server!", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    ACCOUNT_NOT_FOUND("VX-007", "Not found username or email!", HttpStatus.BAD_REQUEST.value()),
    ACCOUNT_WRONG("VX-008", "Username or password incorrect!", HttpStatus.BAD_REQUEST.value()),
    ACCOUNT_ALREADY_EXIST("VX-009", "Account already exist!", HttpStatus.BAD_REQUEST.value()),
    ;

    private String code;

    private final String message;

    private final int status;

    public static DomainCode get(String code) {
        return Arrays.stream(DomainCode.values()).filter(e -> StringUtils.equals(e.getCode(), code))
                .findFirst().orElse(null);
    }
}
