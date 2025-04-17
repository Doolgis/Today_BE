package com.example.today_be.Location;

import com.example.today_be.Location.dto.HotspotDistanceDto;
import com.example.today_be.Location.dto.HotspotDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    // 사용자기준 가까운 거리순으로 정렬된 장소내기
    public static List<HotspotDistanceDto> sortNearHotspots(double userLat, double userLon, List<HotspotDto> hotspotList) {
        double radius = 5.0;

        List<HotspotDistanceDto> nearDistanceHotspotList = new ArrayList<>();
        for (HotspotDto h : hotspotList) {
            double distance = calculateHaversine(userLat, userLon, h.getLatitude(), h.getLongtitude());
            if (distance <= radius) {
                nearDistanceHotspotList.add(new HotspotDistanceDto(distance, h));
            }
        }

        // 거리 오름차순 정렬
        nearDistanceHotspotList.sort(Comparator.comparingDouble(HotspotDistanceDto::getDistance));
        return nearDistanceHotspotList;
    }
}
