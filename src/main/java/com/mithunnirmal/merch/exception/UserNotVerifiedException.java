package com.mithunnirmal.merch.exception;

public class UserNotVerifiedException extends Throwable {
    public UserNotVerifiedException(String token) {
        super(token);
    }

    public UserNotVerifiedException() {
    }
}
