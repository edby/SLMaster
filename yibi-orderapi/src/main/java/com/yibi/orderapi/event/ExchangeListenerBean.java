package com.yibi.orderapi.event;

import com.yibi.core.entity.OrderSpotRecord;
import lombok.Data;

import java.util.List;

@Data
public class ExchangeListenerBean {
    private OrderSpotRecord record;
}
