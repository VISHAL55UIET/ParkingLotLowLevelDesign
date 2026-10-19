package com.nailyourinterview.lld.parking_lot.parking_lot.model;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Vehicle {
    private final String number;
    private final VehicleType type;
}