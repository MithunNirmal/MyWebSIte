package com.mithunnirmal.merch.exception;

public class UserEmailTakenException extends Exception{
    UserEmailTakenException(String str){
        super(str);
    }
    public UserEmailTakenException() {}
}
