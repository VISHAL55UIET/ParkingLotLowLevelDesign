package com.nailyourinterview.lld.parking_lot.parking_lot.model;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;

public class Truck extends Vehicle {
    public Truck(String number) { super(number, VehicleType.TRUCK); }
}