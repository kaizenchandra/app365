package com.synechisveltiosi.apis.app365.common.payment;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DonationRequest {

    @NotBlank
    private String source;

    @Min(value = 1, message = "Minimum donation amount is 1 dollar.")
    @Digits(integer = 3, fraction = 2, message = "Donation amount should be 3 digits and 2 fraction max.")
    private double amount;

    @NotNull
    private Currency currency;

    private String description;

    @NotNull
    private PaymentProcessorType paymentProcessor;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentProcessorType getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(PaymentProcessorType paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
