package com.nailyourinterview.lld.parking_lot.parking_lot.service;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.PaymentStatus;
import com.nailyourinterview.lld.parking_lot.parking_lot.model.Ticket;
import com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment.PaymentStrategy;

public class PaymentProcessor {
    private final PaymentStrategy strategy;

    public PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean pay(Ticket ticket, double amount) {
        boolean success = strategy.processPayment(ticket, amount);
        if (success) {
            ticket.setPaymentStatus(PaymentStatus.SUCCESS);
        } else {
            ticket.setPaymentStatus(PaymentStatus.FAILED);
            System.out.println("Payment failed for ticket: " + ticket.getTicketId());
        }
        return success;
    }
}