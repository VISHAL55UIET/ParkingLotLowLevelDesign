package com.nailyourinterview.lld.parking_lot.parking_lot.model;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.GateType;
import com.nailyourinterview.lld.parking_lot.parking_lot.service.ParkingLot;

import java.time.LocalDateTime;

public class EntryGate extends Gate {

    public EntryGate(String id) {
        super(id);
    }

    @Override
    public GateType getType() {
        return GateType.ENTRY;
    }

    public Ticket parkVehicle(Vehicle vehicle, LocalDateTime entryTime) {
        return ParkingLot.getInstance().parkVehicle(vehicle, entryTime);
    }
}
