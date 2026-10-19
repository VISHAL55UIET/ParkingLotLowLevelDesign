package com.nailyourinterview.lld.parking_lot.parking_lot.model;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;

public class Bike extends Vehicle {
    public Bike(String number) { super(number, VehicleType.BIKE); }
}