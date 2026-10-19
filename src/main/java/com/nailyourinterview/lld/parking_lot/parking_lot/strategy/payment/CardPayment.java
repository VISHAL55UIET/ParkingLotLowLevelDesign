package com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment;

import com.nailyourinterview.lld.parking_lot.parking_lot.model.Ticket;

public class CardPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(Ticket ticket, double amount) {
        System.out.println("Paid ₹" + amount + " for ticket " + ticket.getTicketId() + " via Card.");
        return true;
    }
}