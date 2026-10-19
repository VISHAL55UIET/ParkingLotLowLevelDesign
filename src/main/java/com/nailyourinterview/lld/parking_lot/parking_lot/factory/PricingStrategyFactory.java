package com.nailyourinterview.lld.parking_lot.parking_lot.factory;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.PricingStrategyType;
import com.nailyourinterview.lld.parking_lot.parking_lot.pricing.EventBasedPricing;
import com.nailyourinterview.lld.parking_lot.parking_lot.pricing.PricingStrategy;
import com.nailyourinterview.lld.parking_lot.parking_lot.pricing.TimeBasedPricing;

public class PricingStrategyFactory {
    public static PricingStrategy get(PricingStrategyType type) {
        return switch (type) {
            case TIME_BASED -> new TimeBasedPricing();
            case EVENT_BASED -> new EventBasedPricing();
        };
    }
}