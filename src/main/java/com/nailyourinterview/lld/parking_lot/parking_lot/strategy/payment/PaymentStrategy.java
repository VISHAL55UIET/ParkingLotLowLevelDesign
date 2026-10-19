package com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment;

import com.nailyourinterview.lld.parking_lot.parking_lot.model.Ticket;

public interface PaymentStrategy {
    boolean processPayment(Ticket ticket, double amount);
}