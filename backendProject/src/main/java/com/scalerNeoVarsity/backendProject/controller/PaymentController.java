package com.scalerNeoVarsity.backendProject.controller;

import com.scalerNeoVarsity.backendProject.dto.PaymentRequestDTO;
import com.scalerNeoVarsity.backendProject.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.exception.StripeException;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<String> createPaymentLink(@RequestBody PaymentRequestDTO paymentRequestDTO) throws StripeException {
        String paymentLink = paymentService.makePayment(paymentRequestDTO.getOrderId(), paymentRequestDTO.getAmount());
        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }

    @PostMapping("/webhook")
    public void handleWebhook() {
        System.out.println("Webhook received here");
    }
}
