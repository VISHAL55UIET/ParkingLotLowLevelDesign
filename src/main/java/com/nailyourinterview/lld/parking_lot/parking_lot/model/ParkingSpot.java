package com.nailyourinterview.lld.parking_lot.parking_lot.model;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@RequiredArgsConstructor
public class ParkingSpot {
    private final String id;
    private final VehicleType allowedType;

    private AtomicBoolean occupied = new AtomicBoolean(false);

    // t1 t2
    // false false
    // _     true
    // false


    public boolean tryOccupy() {
        return occupied.compareAndSet(false, true);
    }

    public void vacate() {
        occupied.set(false);
    }

    public boolean isOccupied() {
        return occupied.get();
    }
}