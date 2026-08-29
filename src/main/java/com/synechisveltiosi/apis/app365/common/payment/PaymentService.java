package com.synechisveltiosi.apis.app365.common.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PaymentService {

    void charge(@NotBlank String source, @NotNull Amount amount) throws PaymentException;

    void charge(@NotBlank String source, @NotNull Amount amount, @NotBlank String description) throws PaymentException;
}
