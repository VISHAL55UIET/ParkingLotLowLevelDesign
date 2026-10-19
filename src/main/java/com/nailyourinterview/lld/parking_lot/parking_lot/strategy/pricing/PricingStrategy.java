package com.nailyourinterview.lld.parking_lot.parking_lot.strategy.pricing;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;

import java.time.LocalDateTime;

public interface PricingStrategy {
    double calculateFee(VehicleType type, LocalDateTime entryTime, LocalDateTime exitTime);
}
