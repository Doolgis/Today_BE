package com.example.today_be.Bike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedCourseDto {
    private double startLat;
    private double startLon;
    private double endLat;
    private double endLon;
    private String endStationName;
    private double distanceKm;
}
