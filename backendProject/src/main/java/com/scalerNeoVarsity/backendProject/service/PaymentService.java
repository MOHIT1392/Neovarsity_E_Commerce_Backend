package com.scalerNeoVarsity.backendProject.service;

import com.stripe.exception.StripeException;


public interface PaymentService {
    String makePayment(String orderId, Long amount) throws StripeException;;
}
