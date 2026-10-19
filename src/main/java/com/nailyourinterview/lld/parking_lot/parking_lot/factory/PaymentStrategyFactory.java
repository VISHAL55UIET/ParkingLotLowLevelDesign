package com.nailyourinterview.lld.parking_lot.parking_lot.factory;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.PaymentMode;
import com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment.CardPayment;
import com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment.CashPayment;
import com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment.PaymentStrategy;
import com.nailyourinterview.lld.parking_lot.parking_lot.strategy.payment.UpiPayment;

public class PaymentStrategyFactory {
    public static PaymentStrategy get(PaymentMode mode) {
        return switch (mode) {
            case CASH -> new CashPayment();
            case UPI -> new UpiPayment();
            case CARD -> new CardPayment();
        };
    }
}