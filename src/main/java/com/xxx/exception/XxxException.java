package com.xxx.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class XxxException extends RuntimeException {
    private final DomainCode code;
    private transient Object[] args;
    private HttpStatus status;

    public XxxException(DomainCode code) {
        this.code = code;
    }

    public XxxException(DomainCode code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public XxxException(DomainCode code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }
}
