package com.nailyourinterview.lld.parking_lot.parking_lot.model;


import com.nailyourinterview.lld.parking_lot.parking_lot.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Ticket {
    private String ticketId;
    private LocalDateTime entryTime;
    private Vehicle vehicle;
    private String floorId;
    private String spotId;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
}