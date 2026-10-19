package com.nailyourinterview.lld.parking_lot.parking_lot.factory;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;
import com.nailyourinterview.lld.parking_lot.parking_lot.model.Bike;
import com.nailyourinterview.lld.parking_lot.parking_lot.model.Car;
import com.nailyourinterview.lld.parking_lot.parking_lot.model.Truck;
import com.nailyourinterview.lld.parking_lot.parking_lot.model.Vehicle;

public class VehicleFactory {
    public static Vehicle create(String number, VehicleType type) {
        return switch (type) {
            case CAR -> new Car(number);
            case BIKE -> new Bike(number);
            case TRUCK -> new Truck(number);
        };
    }
}