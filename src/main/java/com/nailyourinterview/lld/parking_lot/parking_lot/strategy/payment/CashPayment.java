package com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment;

import com.nailyourinterview.lld.parking_lot.parking_lot.model.Ticket;

public class CashPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(Ticket ticket, double amount) {
        System.out.println("Paid ₹" + amount + " for ticket " + ticket.getTicketId() + " via Cash.");
        return true;
    }
}