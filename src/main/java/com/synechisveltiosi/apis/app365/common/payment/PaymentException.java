package com.synechisveltiosi.apis.app365.common.payment;

public class PaymentException extends Exception {

    public PaymentException() {

    }

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
