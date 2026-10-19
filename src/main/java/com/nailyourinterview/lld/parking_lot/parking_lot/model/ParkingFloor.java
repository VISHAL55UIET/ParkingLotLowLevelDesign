package com.nailyourinterview.lld.parking_lot.parking_lot.model;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ParkingFloor {
    private final String id;
    private final Map<String, ParkingSpot> spots = new HashMap<>();

    public ParkingFloor(String id) { this.id = id; }

    public void addSpot(ParkingSpot spot) {
        spots.put(spot.getId(), spot);
    }

    public ParkingSpot findAvailableSpot(VehicleType vehicleType) {
        for (ParkingSpot spot : spots.values()) {
            if (spot.getAllowedType() == vehicleType && spot.tryOccupy()) {
                return spot;
            }
        }
        return null;
    }
}