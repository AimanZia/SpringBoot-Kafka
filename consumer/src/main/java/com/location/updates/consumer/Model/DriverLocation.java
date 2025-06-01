package com.location.updates.consumer.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverLocation {
    private String driverId;
    private double latitude;
    private double longitude;
    private String timestamp;
}
