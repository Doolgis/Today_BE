package com.example.today_be.Location;
import com.example.today_be.Location.dto.HotspotDistanceDto;
import com.example.today_be.Location.dto.HotspotDto;
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

    public List<HotspotDto> getNearHotspots(double userLat, double userLon) {
        List<HotspotDto> hotspotList = reader.loadHotspots();
        return DistanceService.findNearHotspots(userLat, userLon, hotspotList);
    }


    public List<HotspotDistanceDto> getSortedHotspots(double userLat, double userLon) {
        List<HotspotDto> allHotspots = reader.loadHotspots();
        return DistanceService.sortNearHotspots(userLat, userLon, allHotspots);
    }

}
