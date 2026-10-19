package com.nailyourinterview.lld.parking_lot.parking_lot.model;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.GateType;
import com.nailyourinterview.lld.parking_lot.parking_lot.enums.PaymentMode;
import com.nailyourinterview.lld.parking_lot.parking_lot.service.ParkingLot;

import java.time.LocalDateTime;

public class ExitGate extends Gate {

    public ExitGate(String id) {
        super(id);
    }

    @Override
    public GateType getType() {
        return GateType.EXIT;
    }

    public void unparkVehicle(String ticketId, LocalDateTime exitTime, PaymentMode paymentMode) {
        ParkingLot.getInstance().unparkVehicle(ticketId, exitTime, paymentMode);
    }
}