package com.example.today_be.Location;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final HotspotCsvReader reader;

    public LocationService(HotspotCsvReader reader) {
        this.reader = reader;
    }

    public void printHotspotList() {
        List<HotspotDto> hotspots =reader.loadHotspots();
        for (HotspotDto dto : hotspots) {
            System.out.println(dto);
        }
    }

    public List<HotspotDto> getAllHotspots() {
        return reader.loadHotspots();
    }
}
