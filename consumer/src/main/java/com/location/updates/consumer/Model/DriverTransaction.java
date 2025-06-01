package com.location.updates.consumer.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverTransaction {
    private String transactionId;
    private String userId;
    private String driverId;
}
