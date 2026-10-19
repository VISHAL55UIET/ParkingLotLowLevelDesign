package com.nailyourinterview.lld.parking_lot.parking_lot;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.PricingStrategyType;
import com.nailyourinterview.lld.parking_lot.parking_lot.enums.VehicleType;
import com.nailyourinterview.lld.parking_lot.parking_lot.factory.PricingStrategyFactory;
import com.nailyourinterview.lld.parking_lot.parking_lot.factory.VehicleFactory;
import com.nailyourinterview.lld.parking_lot.parking_lot.model.*;
import com.nailyourinterview.lld.parking_lot.parking_lot.service.ParkingLot;
import com.nailyourinterview.lld.parking_lot.parking_lot.utils.DateTimeParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class ParkingLotApplication {

    public static void main(String[] args) {
        ParkingLot lot = ParkingLot.getInstance();
        EntryGate entryGate = new EntryGate("EG1");
        ExitGate exitGate = new ExitGate("XG1");

        lot.setPricingStrategy(PricingStrategyFactory.get(PricingStrategyType.valueOf("EVENT_BASED")));

        ParkingFloor floor1 = new ParkingFloor("Floor1");
        floor1.addSpot(new ParkingSpot("F1S1", VehicleType.BIKE));
        floor1.addSpot(new ParkingSpot("F1S2", VehicleType.CAR));
        floor1.addSpot(new ParkingSpot("F1S3", VehicleType.TRUCK));
        floor1.addSpot(new ParkingSpot("F1S4", VehicleType.CAR));
        lot.addFloor(floor1);

        System.out.println("--------------------------");

        Vehicle bike1 = VehicleFactory.create("KA01AB1234", VehicleType.BIKE);
        Vehicle bike2 = VehicleFactory.create("KA01AB5678", VehicleType.BIKE);
        LocalDateTime entryTime = DateTimeParser.parse("21 May 7:30 AM 2025");
        System.out.println(entryTime.truncatedTo(ChronoUnit.HOURS));

        Thread t1 = new Thread(() -> entryGate.parkVehicle(bike1, entryTime));
        Thread t2 = new Thread(() -> entryGate.parkVehicle(bike2, entryTime));

        t1.start();
        t2.start();

//        Vehicle car = VehicleFactory.create("KA01AB1234", VehicleType.SEDAN);
//
//        LocalDateTime entryTime = DateTimeParser.parse("21 May 7:30 AM 2025");
//        Ticket ticket = entryGate.parkVehicle(car, entryTime);
//
//        System.out.println("--------------------------");
//
//        lot.printStatus();
//
//        System.out.println("--------------------------");
//
//        LocalDateTime exitTime = DateTimeParser.parse("21 May 1:15 PM 2025");
//        exitGate.unparkVehicle(ticket.getTicketId(), exitTime, PaymentMode.UPI);
//
//        System.out.println("--------------------------");
//
//        lot.printStatus();
    }

}
