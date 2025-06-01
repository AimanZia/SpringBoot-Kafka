package com.location.updates.producer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.location.updates.producer.Service.LocationPublisherService;
import com.location.updates.producer.model.DriverLocation;
import com.location.updates.producer.model.DriverTransaction;

@RestController
@RequestMapping("/publisher")
public class LocationPubController {

    @Autowired
    private LocationPublisherService locationPublisherService;

    @PostMapping("/updateLocation")
    public String updateLocation(@RequestBody DriverLocation location) {
        locationPublisherService.publishLocation(location);
        return "Location update published successfully.";
    }

    @PostMapping("/updatePayment")
    public String postMethodName(@RequestBody DriverTransaction transaction) {
        locationPublisherService.publishTransaction(transaction);
        return "Payment Update Processed Sucessfully";
    }
    
}
