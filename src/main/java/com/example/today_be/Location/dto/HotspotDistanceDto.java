package com.example.today_be.Location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotspotDistanceDto {
    private double distance;
    private HotspotDto hotspot;
}
