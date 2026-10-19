package com.nailyourinterview.lld.parking_lot.parking_lot.model;

import com.nailyourinterview.lld.parking_lot.parking_lot.enums.GateType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Gate {
    protected final String id;

    public abstract GateType getType();
}