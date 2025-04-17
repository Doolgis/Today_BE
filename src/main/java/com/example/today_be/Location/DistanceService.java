package com.example.today_be.Location;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistanceService {

    // Haversine 공식(두 지점사이의 거리구하기)
    public static double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371;

        double lat11 = Math.toRadians(lat1);
        double lat22 = Math.toRadians(lat2);
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat11 * lat22) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // 반경 5km이내로 장소거리계산 하기
    public static List<HotspotDto> findNearHotspots(double userLat, double userLon, List<HotspotDto> hotspotList) {
        double radius = 5.0;

        List<HotspotDto> nearHotspots = new ArrayList<>();
        for (HotspotDto h : hotspotList) {
            double distance = calculateHaversine(userLat, userLon, h.getLatitude(), h.getLongtitude());
            if (distance <= radius) {
                nearHotspots.add(h);
            }
        }
        return nearHotspots;
    }
}
