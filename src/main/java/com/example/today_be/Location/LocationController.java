package com.example.today_be.Location;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationService location;

    public LocationController(LocationService location) {
        this.location = location;
    }

    @GetMapping("/")
    public List<HotspotDto> getNearbyHotspots(@RequestParam double userLat, @RequestParam double userLon) {
        return location.getNearHotspots(userLat, userLon);
    }

    @GetMapping("/all")
    public List<HotspotDto> getAllHotspots() {
        return location.getAllHotspots();
    }

}
