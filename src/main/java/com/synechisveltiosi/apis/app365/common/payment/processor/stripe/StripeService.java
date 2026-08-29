package com.synechisveltiosi.apis.app365.common.payment.processor.stripe;

import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.synechisveltiosi.apis.app365.common.payment.Amount;
import com.synechisveltiosi.apis.app365.common.payment.PaymentException;
import com.synechisveltiosi.apis.app365.common.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(StripeService.class);

    private final RequestOptions requestOptions;

    @Autowired
    public StripeService(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    @Override
    public void charge(@NotBlank String source, @NotNull Amount amount) throws PaymentException {
        this.charge(source, amount, "One time donation.");
    }

    @Override
    @SuppressWarnings("TryWithIdenticalCatches")
    public void charge(@NotBlank String source, @NotNull Amount amount, @NotBlank String description)
            throws PaymentException {

        // Convert amount to cents
        long total = (long) (amount.getTotal() * 100);

        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", total);
        chargeMap.put("currency", amount.getCurrency().name().toLowerCase());
        chargeMap.put("source", source);
        chargeMap.put("description", description);

        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            logger.info("Payment information", charge);
        } catch (AuthenticationException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (InvalidRequestException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (APIConnectionException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (CardException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (APIException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
