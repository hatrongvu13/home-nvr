package com.xxx.factory;

import com.xxx.data.response.ApiResponse;
import com.xxx.exception.DomainCode;
import com.xxx.exception.XxxException;
import com.xxx.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.xxx.exception.DomainCode.*;

@Component
@RequiredArgsConstructor
public class ApiFactory {
    private final MessageSource messageSource;

    public ResponseEntity<ApiResponse> success() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse()
                        .withCode(SUCCESS.getCode())
                        .withMessage(messageSource.getMessage(SUCCESS.getCode(), null, Utils.locale())));
    }

    public ResponseEntity<ApiResponse> success(DomainCode domainCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse()
                        .withCode(domainCode.getCode())
                        .withMessage(messageSource.getMessage(domainCode.getCode(), null, Utils.locale())));
    }

    public ResponseEntity<ApiResponse> success(DomainCode domainCode, Object... args) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse()
                        .withCode(domainCode.getCode())
                        .withMessage(messageSource.getMessage(domainCode.getCode(), args, Utils.locale())));
    }

    public ResponseEntity<ApiResponse> success(Object data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse()
                        .withCode(SUCCESS.getCode())
                        .withMessage(messageSource.getMessage(SUCCESS.getCode(), null, Utils.locale()))
                        .withData(data));
    }

    public ResponseEntity<ApiResponse> failWithInternalException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse()
                        .withCode(INTERNAL_SERVER.getCode())
                        .withMessage(messageSource.getMessage(INTERNAL_SERVER.getCode(), null, Utils.locale())));
    }

    public ResponseEntity<ApiResponse> failWithBadInputParameter(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse()
                        .withCode(ERROR.getCode())
                        .withMessage(messageSource.getMessage(ERROR.getCode(), null, Utils.locale())));
    }

    public ResponseEntity<ApiResponse> failWithDomainException(XxxException xxxException) {
        return ResponseEntity.status(xxxException.getCode().getStatus())
                .body(new ApiResponse()
                        .withCode(xxxException.getCode().getCode())
                        .withMessage(messageSource.getMessage(xxxException.getCode().getCode(), null, Utils.locale())));
    }
}
