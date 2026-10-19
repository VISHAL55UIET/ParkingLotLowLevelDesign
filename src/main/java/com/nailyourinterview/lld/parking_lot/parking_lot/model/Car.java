package com.nailyourinterview.lld.parking_lot.parking_lot.model;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;

public class Car extends Vehicle {
    public Car(String number) { super(number, VehicleType.CAR); }
}